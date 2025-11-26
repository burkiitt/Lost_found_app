package ru.relex.jakartaee_project.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import okhttp3.*;
import ru.relex.jakartaee_project.utils.PropertiesUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service for generating text embeddings using Hugging Face Inference API
 * Falls back to simple keyword matching if API is unavailable
 */
public class EmbeddingService {
    private static final EmbeddingService INSTANCE = new EmbeddingService();
    private static final String API_URL = PropertiesUtil.get("ai.embedding.api.url");
    private static final String API_KEY = PropertiesUtil.get("ai.embedding.api.key");
    private static final OkHttpClient client = new OkHttpClient();
    private static final Gson gson = new Gson();
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    private EmbeddingService() {}

    public static EmbeddingService getInstance() {
        return INSTANCE;
    }

    /**
     * Generate embedding vector for a text
     * @param text Input text to embed
     * @return List of floats representing the embedding vector, or null if failed
     */
    public List<Float> generateEmbedding(String text) {
        if (text == null || text.trim().isEmpty()) {
            return null;
        }

        try {
            // Prepare request body - Router API should accept same format as inference API
            // Use string format: {"inputs": "text"}
            String escapedText = text.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", " ")
                    .replace("\r", "")
                    .replace("\t", " ");
            String jsonBody = "{\"inputs\":\"" + escapedText + "\"}";
            RequestBody body = RequestBody.create(jsonBody, JSON);

            // Build request
            Request.Builder requestBuilder = new Request.Builder()
                    .url(API_URL)
                    .post(body)
                    .header("Content-Type", "application/json");

            // Add API key if provided (REQUIRED for router API)
            if (API_KEY != null && !API_KEY.isEmpty()) {
                requestBuilder.header("Authorization", "Bearer " + API_KEY);
            } else {
                System.err.println("WARNING: No API key provided - router API requires authentication!");
            }

            Request request = requestBuilder.build();

            // Execute request
            try (Response response = client.newCall(request).execute()) {
                if (!response.isSuccessful()) {
                    String errorBody = response.body() != null ? response.body().string() : "";
                    System.err.println("Embedding API error: " + response.code() + " - " + response.message());
                    System.err.println("Error details: " + errorBody);
                    
                    // If model is loading (503), wait and retry once
                    if (response.code() == 503) {
                        System.out.println("Model is loading, waiting 10 seconds and retrying...");
                        Thread.sleep(10000);
                        return generateEmbedding(text); // Retry once
                    }
                    
                    return null;
                }

                String responseBody = response.body().string();
                
                // Handle different response formats
                // Sometimes it's a nested array: [[0.1, 0.2, ...]]
                // Sometimes it's a direct array: [0.1, 0.2, ...]
                JsonArray jsonArray;
                try {
                    com.google.gson.JsonElement jsonElement = gson.fromJson(responseBody, com.google.gson.JsonElement.class);
                    if (jsonElement.isJsonArray()) {
                        jsonArray = jsonElement.getAsJsonArray();
                        // If first element is also an array, unwrap it
                        if (jsonArray.size() > 0 && jsonArray.get(0).isJsonArray()) {
                            jsonArray = jsonArray.get(0).getAsJsonArray();
                        }
                    } else {
                        System.err.println("Unexpected response format: " + responseBody);
                        return null;
                    }
                } catch (Exception e) {
                    System.err.println("Error parsing response: " + e.getMessage());
                    System.err.println("Response was: " + responseBody);
                    return null;
                }

                // Convert JSON array to List<Float>
                List<Float> embedding = new ArrayList<>();
                for (JsonElement element : jsonArray) {
                    embedding.add(element.getAsFloat());
                }

                return embedding;
            }
        } catch (IOException e) {
            System.err.println("Error calling embedding API: " + e.getMessage());
            e.printStackTrace();
            return null;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interrupted while waiting for model: " + e.getMessage());
            return null;
        } catch (Exception e) {
            System.err.println("Error processing embedding response: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Calculate cosine similarity between two embedding vectors
     * @param vec1 First vector
     * @param vec2 Second vector
     * @return Cosine similarity score (0-1, higher is more similar)
     */
    public double cosineSimilarity(List<Float> vec1, List<Float> vec2) {
        if (vec1 == null || vec2 == null || vec1.size() != vec2.size()) {
            return 0.0;
        }

        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;

        for (int i = 0; i < vec1.size(); i++) {
            dotProduct += vec1.get(i) * vec2.get(i);
            norm1 += vec1.get(i) * vec1.get(i);
            norm2 += vec2.get(i) * vec2.get(i);
        }

        double denominator = Math.sqrt(norm1) * Math.sqrt(norm2);
        if (denominator == 0.0) {
            return 0.0;
        }

        return dotProduct / denominator;
    }

    /**
     * Convert embedding list to comma-separated string for database storage
     */
    public String embeddingToString(List<Float> embedding) {
        if (embedding == null || embedding.isEmpty()) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < embedding.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append(embedding.get(i));
        }
        return sb.toString();
    }

    /**
     * Parse embedding string from database to List<Float>
     */
    public List<Float> stringToEmbedding(String embeddingStr) {
        if (embeddingStr == null || embeddingStr.trim().isEmpty()) {
            return null;
        }
        List<Float> embedding = new ArrayList<>();
        String[] parts = embeddingStr.split(",");
        for (String part : parts) {
            try {
                embedding.add(Float.parseFloat(part.trim()));
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return embedding;
    }

}

