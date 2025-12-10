package ru.relex.jakartaee_project.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.relex.jakartaee_project.dao.EmbeddingDao;
import ru.relex.jakartaee_project.dao.ImageDao;
import ru.relex.jakartaee_project.dao.ItemDao;
import ru.relex.jakartaee_project.dto.ItemDto;
import ru.relex.jakartaee_project.entity.Item;
import ru.relex.jakartaee_project.service.ai.ClipClient;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/searchByImage")
@MultipartConfig
public class ImageSearchServlet extends HttpServlet {

    // Minimum similarity threshold (0.55 = 55%)
    // Only items with similarity score above this threshold will be shown
    private static final double MIN_SIMILARITY_THRESHOLD = 0.55;
    private static final int MAX_RESULTS = 50;

    private final ClipClient clip = new ClipClient();
    private final EmbeddingDao embeddingDao = new EmbeddingDao();
    private final ItemDao itemDao = ItemDao.getInstance();
    private final ImageDao imageDao = ImageDao.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/views/search.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Part file = req.getPart("photo");
        if (file == null || file.getSize() == 0) {
            req.setAttribute("error", "Please upload an image");
            req.getRequestDispatcher("WEB-INF/views/search.jsp").forward(req, resp);
            return;
        }

        byte[] imageBytes = file.getInputStream().readAllBytes();

        try {
            // 1. Get embedding of uploaded image
            float[] queryVec = clip.getImageEmbedding(imageBytes);

            // 2. Get all embeddings with their item IDs
            List<EmbeddingDao.ItemEmbedding> itemEmbeddings = embeddingDao.getAllEmbeddingsWithItemIds();

            if (itemEmbeddings.isEmpty()) {
                req.setAttribute("error", "No items with images found in database. Please add items with images first.");
                req.getRequestDispatcher("WEB-INF/views/search.jsp").forward(req, resp);
                return;
            }

            // 3. Prepare vectors array (maintaining order)
            float[][] dbArray = new float[itemEmbeddings.size()][];
            for (int i = 0; i < itemEmbeddings.size(); i++) {
                dbArray[i] = itemEmbeddings.get(i).getEmbedding();
            }

            // 4. Get similarity scores
            double[] scores = clip.searchSimilar(queryVec, dbArray);

            // 5. Create list of item IDs with scores and sort by score (descending)
            List<ItemWithScore> itemsWithScores = new ArrayList<>();
            for (int i = 0; i < itemEmbeddings.size(); i++) {
                itemsWithScores.add(new ItemWithScore(
                    itemEmbeddings.get(i).getItemId(),
                    scores[i]
                ));
            }

            // Sort by similarity score (highest first) and filter low scores
            itemsWithScores.sort((a, b) -> Double.compare(b.score, a.score));
            itemsWithScores = itemsWithScores.stream()
                .filter(item -> item.score >= MIN_SIMILARITY_THRESHOLD) // Only show items with >= 55% similarity
                .limit(MAX_RESULTS) // Limit to top results
                .collect(Collectors.toList());

            // 6. Get actual items from database
            List<Long> itemIds = itemsWithScores.stream()
                .map(item -> (long) item.itemId)
                .collect(Collectors.toList());

            List<Item> items = itemDao.findByIds(itemIds);
            Map<Long, Item> itemMap = items.stream()
                .collect(Collectors.toMap(Item::getId, item -> item));

            // 7. Create ItemDto list maintaining score order
            List<ItemDto> resultItems = new ArrayList<>();
            Map<Long, Double> scoreMap = new HashMap<>();
            for (ItemWithScore itemWithScore : itemsWithScores) {
                Item item = itemMap.get((long) itemWithScore.itemId);
                if (item != null) {
                    resultItems.add(new ItemDto(
                        item.getId(),
                        item.getTitle(),
                        item.getDescription(),
                        imageDao.findByItemID(item.getId())
                    ));
                    scoreMap.put(item.getId(), itemWithScore.score);
                }
            }

            // 8. Pass results to JSP
            req.setAttribute("items", resultItems);
            req.setAttribute("scores", scoreMap);
            req.setAttribute("threshold", MIN_SIMILARITY_THRESHOLD);
            if (resultItems.isEmpty()) {
                req.setAttribute("noResultsMessage", 
                    String.format("No items found with similarity >= %.0f%%. Try uploading a different image.", 
                        MIN_SIMILARITY_THRESHOLD * 100));
            }
            req.getRequestDispatcher("WEB-INF/views/search_results.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            req.setAttribute("error", "Error processing image: " + e.getMessage());
            req.getRequestDispatcher("WEB-INF/views/search.jsp").forward(req, resp);
        }
    }

    /**
     * Helper class to hold item ID and similarity score
     */
    private static class ItemWithScore {
        final int itemId;
        final double score;

        ItemWithScore(int itemId, double score) {
            this.itemId = itemId;
            this.score = score;
        }
    }
}
