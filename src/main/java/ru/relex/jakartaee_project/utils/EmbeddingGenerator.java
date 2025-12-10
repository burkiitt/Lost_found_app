package ru.relex.jakartaee_project.utils;

import ru.relex.jakartaee_project.dao.EmbeddingDao;
import ru.relex.jakartaee_project.dao.ItemDao;
import ru.relex.jakartaee_project.entity.Item;
import ru.relex.jakartaee_project.service.EmbeddingService;

import java.util.List;

/**
 * Utility class to generate embeddings for existing items in the database
 * Run this once to populate embeddings for all existing items
 */
/*public class EmbeddingGenerator {
    
    public static void main(String[] args) {
        System.out.println("Starting embedding generation for existing items...");
        
        ItemDao itemDao = ItemDao.getInstance();
        EmbeddingDao embeddingDao = EmbeddingDao.getInstance();
        EmbeddingService embeddingService = EmbeddingService.getInstance();
        
        List<Item> items = itemDao.findAll();
        System.out.println("Found " + items.size() + " items to process");
        
        int successCount = 0;
        int failCount = 0;
        
        for (Item item : items) {
            if (item.getDescription() != null && !item.getDescription().trim().isEmpty()) {
                try {
                    System.out.println("Processing item ID: " + item.getId() + " - " + item.getTitle());
                    
                    List<Float> embedding = embeddingService.generateEmbedding(item.getDescription());
                    if (embedding != null && !embedding.isEmpty()) {
                        String embeddingStr = embeddingService.embeddingToString(embedding);
                        if (embeddingDao.saveEmbedding(item.getId(), embeddingStr)) {
                            successCount++;
                            System.out.println("✓ Successfully generated embedding for item " + item.getId());
                        } else {
                            failCount++;
                            System.out.println("✗ Failed to save embedding for item " + item.getId());
                        }
                    } else {
                        failCount++;
                        System.out.println("✗ Failed to generate embedding for item " + item.getId());
                    }
                    
                    // Small delay to avoid rate limiting
                    Thread.sleep(100);
                } catch (Exception e) {
                    failCount++;
                    System.err.println("Error processing item " + item.getId() + ": " + e.getMessage());
                }
            } else {
                System.out.println("Skipping item " + item.getId() + " (no description)");
            }
        }
        
        System.out.println("\n=== Summary ===");
        System.out.println("Success: " + successCount);
        System.out.println("Failed: " + failCount);
        System.out.println("Total: " + items.size());
    }
}*/







