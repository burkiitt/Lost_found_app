package ru.relex.jakartaee_project.dao;

import com.google.gson.Gson;
import ru.relex.jakartaee_project.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmbeddingDao {

    private final Gson gson = new Gson();

    public void saveEmbedding(int itemId, float[] embedding) throws SQLException {
        String sql = "INSERT INTO item_embeddings (item_id, embedding) VALUES (?, ?::json)";

        try (Connection conn = ConnectionManager.get();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, itemId);
            ps.setString(2, gson.toJson(embedding));
            ps.executeUpdate();
        }
    }

    public List<float[]> getAllEmbeddings() throws SQLException {
        String sql = "SELECT embedding FROM item_embeddings";

        List<float[]> list = new ArrayList<>();

        try (Connection conn = ConnectionManager.get();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String json = rs.getString("embedding");
                float[] vector = gson.fromJson(json, float[].class);
                list.add(vector);
            }
        }

        return list;
    }

    /**
     * Get all embeddings with their item IDs
     * Returns a list of ItemEmbedding objects containing item_id and embedding vector
     */
    public List<ItemEmbedding> getAllEmbeddingsWithItemIds() throws SQLException {
        String sql = "SELECT item_id, embedding FROM item_embeddings";

        List<ItemEmbedding> list = new ArrayList<>();

        try (Connection conn = ConnectionManager.get();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int itemId = rs.getInt("item_id");
                String json = rs.getString("embedding");
                float[] vector = gson.fromJson(json, float[].class);
                list.add(new ItemEmbedding(itemId, vector));
            }
        }

        return list;
    }

    /**
     * Inner class to hold item_id and embedding together
     */
    public static class ItemEmbedding {
        private final int itemId;
        private final float[] embedding;

        public ItemEmbedding(int itemId, float[] embedding) {
            this.itemId = itemId;
            this.embedding = embedding;
        }

        public int getItemId() {
            return itemId;
        }

        public float[] getEmbedding() {
            return embedding;
        }
    }
}



