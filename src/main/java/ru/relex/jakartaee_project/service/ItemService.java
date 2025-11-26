package ru.relex.jakartaee_project.service;

import ru.relex.jakartaee_project.dao.CategoryDao;
import ru.relex.jakartaee_project.dao.EmbeddingDao;
import ru.relex.jakartaee_project.dao.ImageDao;
import ru.relex.jakartaee_project.dao.ItemDao;
import ru.relex.jakartaee_project.dao.UserDao;
import ru.relex.jakartaee_project.dto.FullItemDto;
import ru.relex.jakartaee_project.dto.ItemDto;
import ru.relex.jakartaee_project.entity.Category;
import ru.relex.jakartaee_project.entity.Image;
import ru.relex.jakartaee_project.entity.Item;
import ru.relex.jakartaee_project.entity.User;
import ru.relex.jakartaee_project.utils.PropertiesUtil;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ItemService {
    public static final ItemService INSTANCE = new ItemService();

    public static final ItemDao itemDao = ItemDao.getInstance();

    public static final ImageDao imageDao = ImageDao.getInstance();

    private static final EmbeddingService embeddingService = EmbeddingService.getInstance();
    private static final EmbeddingDao embeddingDao = EmbeddingDao.getInstance();
    private static final boolean AI_SEARCH_ENABLED = 
        "true".equalsIgnoreCase(PropertiesUtil.get("ai.search.enabled"));

    private ItemService() {}

    public static ItemService getInstance() {
        return INSTANCE;
    }

    public List<ItemDto> findAll() {
        return itemDao.findAll().stream().map(item ->
                new ItemDto(item.getId(), item.getTitle(),
                        item.getDescription(),
                        imageDao.findByItemID(item.getId()))).collect(Collectors.toList());
    }
    public List<ItemDto> searchItems(String name, String category, String type, String dateFrom, String dateTo) {
        // If AI search is enabled and we have a search query, try semantic search
        if (AI_SEARCH_ENABLED && name != null && !name.trim().isEmpty()) {
            List<ItemDto> semanticResults = semanticSearch(name, category, type, dateFrom, dateTo);
            if (semanticResults != null && !semanticResults.isEmpty()) {
                return semanticResults;
            }
            // Fall back to keyword search if semantic search fails
        }
        
        // Standard keyword-based search
        return itemDao.searchItems(name, category, type, dateFrom, dateTo).stream().map(item ->
                new ItemDto(item.getId(), item.getTitle(),
                        item.getDescription(),
                        imageDao.findByItemID(item.getId()))).collect(Collectors.toList());
    }

    /**
     * Semantic search using embeddings
     * Returns items sorted by similarity to the search query
     */
    private List<ItemDto> semanticSearch(String query, String category, String type, String dateFrom, String dateTo) {
        try {
            // Generate embedding for search query
            List<Float> queryEmbedding = embeddingService.generateEmbedding(query);
            if (queryEmbedding == null || queryEmbedding.isEmpty()) {
                return null; // Fall back to keyword search
            }

            // Get all item embeddings
            List<EmbeddingDao.ItemEmbedding> itemEmbeddings = embeddingDao.getAllEmbeddings();
            if (itemEmbeddings.isEmpty()) {
                return null; // No embeddings available, fall back
            }

            // Calculate similarity scores
            Map<Long, Double> similarityScores = new HashMap<>();
            for (EmbeddingDao.ItemEmbedding itemEmbedding : itemEmbeddings) {
                List<Float> itemEmbeddingVec = embeddingService.stringToEmbedding(itemEmbedding.getEmbeddingStr());
                if (itemEmbeddingVec != null) {
                    double similarity = embeddingService.cosineSimilarity(queryEmbedding, itemEmbeddingVec);
                    similarityScores.put(itemEmbedding.getItemId(), similarity);
                }
            }

            // Sort by similarity (highest first) and get top results
            List<Long> topItemIds = similarityScores.entrySet().stream()
                    .sorted(Map.Entry.<Long, Double>comparingByValue().reversed())
                    .filter(entry -> entry.getValue() > 0.3) // Minimum similarity threshold
                    .limit(50) // Limit results
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());

            if (topItemIds.isEmpty()) {
                return null; // No similar items found
            }

            // Get items by IDs
            List<Item> items = itemDao.findByIds(topItemIds);

            // Apply additional filters (category, type, date)
            items = items.stream()
                    .filter(item -> category == null || category.isEmpty() || 
                            matchesCategory(item, category))
                    .filter(item -> type == null || type.isEmpty() || 
                            item.getType().equalsIgnoreCase(type))
                    .filter(item -> dateFrom == null || dateFrom.isEmpty() || 
                            item.getEvent_date().toLocalDate().compareTo(
                                java.time.LocalDate.parse(dateFrom)) >= 0)
                    .filter(item -> dateTo == null || dateTo.isEmpty() || 
                            item.getEvent_date().toLocalDate().compareTo(
                                java.time.LocalDate.parse(dateTo)) <= 0)
                    .collect(Collectors.toList());

            // Convert to DTOs and maintain similarity order
            Map<Long, Item> itemMap = items.stream()
                    .collect(Collectors.toMap(Item::getId, item -> item));

            return topItemIds.stream()
                    .filter(itemMap::containsKey)
                    .map(itemMap::get)
                    .map(item -> new ItemDto(item.getId(), item.getTitle(),
                            item.getDescription(),
                            imageDao.findByItemID(item.getId())))
                    .collect(Collectors.toList());

        } catch (Exception e) {
            System.err.println("Error in semantic search: " + e.getMessage());
            e.printStackTrace();
            return null; // Fall back to keyword search on error
        }
    }

    private boolean matchesCategory(Item item, String categoryName) {
        try {
            Category category = CategoryDao.getInstance().findById(item.getCategory_id());
            return category != null && category.getName().equalsIgnoreCase(categoryName);
        } catch (Exception e) {
            return false;
        }
    }

    public FullItemDto findByIdFull(long id) {
        Item item = itemDao.findById(id);
        if (item == null) return null;

        User user = UserDao.getInstance().findById(item.getUser_id());
        Category category = CategoryDao.getInstance().findById(item.getCategory_id());
        List<Image> images = ImageDao.getInstance().findByItemID(item.getId());

        return new FullItemDto(
                item.getId(),
                item.getTitle(),
                item.getDescription(),
                item.getLocation(),
                item.getEvent_date(),
                item.getStatus(),
                user,
                category,
                images
        );
    }
    public long createItem(long userId, long categoryId, String type,
                           String title, String desc, String location,
                           LocalDateTime eventDate) {
        Item item = new Item(userId, categoryId, type, title, desc, location, eventDate);
        long itemId = itemDao.save_return_id(item);
        
        // Generate and save embedding if AI search is enabled
        if (AI_SEARCH_ENABLED && desc != null && !desc.trim().isEmpty()) {
            generateAndSaveEmbedding(itemId, desc);
        }
        
        return itemId;
    }

    /**
     * Generate and save embedding for an item description
     * Runs asynchronously to avoid blocking item creation
     */
    private void generateAndSaveEmbedding(Long itemId, String description) {
        // Run in background thread to avoid blocking
        new Thread(() -> {
            try {
                List<Float> embedding = embeddingService.generateEmbedding(description);
                if (embedding != null && !embedding.isEmpty()) {
                    String embeddingStr = embeddingService.embeddingToString(embedding);
                    embeddingDao.saveEmbedding(itemId, embeddingStr);
                }
            } catch (Exception e) {
                System.err.println("Error generating embedding for item " + itemId + ": " + e.getMessage());
            }
        }).start();
    }

    public List<ItemDto> findItemsByUserId(Long id) {
        return itemDao.itemsByUserId(id).stream().map(item ->
                new ItemDto(item.getId(), item.getTitle(),
                        item.getDescription(),
                        imageDao.findByItemID(item.getId()))).collect(Collectors.toList());
    }

    public boolean deleteItem(Long id) {
        // First delete all associated images
        imageDao.deleteByItemId(id);
        // Then delete the item
        return itemDao.delete(id);
    }

}