package ru.relex.jakartaee_project.dao;

import ru.relex.jakartaee_project.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for managing item embeddings in the database
 */
public class EmbeddingDao {
    private static final EmbeddingDao instance = new EmbeddingDao();

    private EmbeddingDao() {}

    public static EmbeddingDao getInstance() {
        return instance;
    }

    /**
     * Save or update embedding for an item
     * Creates embedding column if it doesn't exist
     */
    public boolean saveEmbedding(Long itemId, String embeddingStr) {
        try (Connection connection = ConnectionManager.get()) {
            // Check if embedding column exists, if not create it
            ensureEmbeddingColumnExists(connection);

            String sql = """
                UPDATE items 
                SET description_embedding = ? 
                WHERE id = ?
                """;

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, embeddingStr);
                ps.setLong(2, itemId);
                return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error saving embedding: " + e.getMessage());
            return false;
        }
    }

    /**
     * Get embedding for an item
     */
    public String getEmbedding(Long itemId) {
        try (Connection connection = ConnectionManager.get()) {
            ensureEmbeddingColumnExists(connection);

            String sql = """
                SELECT description_embedding 
                FROM items 
                WHERE id = ?
                """;

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setLong(1, itemId);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return rs.getString("description_embedding");
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting embedding: " + e.getMessage());
        }
        return null;
    }

    /**
     * Get all items with their embeddings for similarity search
     */
    public List<ItemEmbedding> getAllEmbeddings() {
        List<ItemEmbedding> embeddings = new ArrayList<>();
        try (Connection connection = ConnectionManager.get()) {
            ensureEmbeddingColumnExists(connection);

            String sql = """
                SELECT id, description_embedding 
                FROM items 
                WHERE description_embedding IS NOT NULL
                """;

            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    Long id = rs.getLong("id");
                    String embeddingStr = rs.getString("description_embedding");
                    if (embeddingStr != null && !embeddingStr.isEmpty()) {
                        embeddings.add(new ItemEmbedding(id, embeddingStr));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting all embeddings: " + e.getMessage());
        }
        return embeddings;
    }

    /**
     * Ensure the embedding column exists in the items table
     */
    private void ensureEmbeddingColumnExists(Connection connection) throws SQLException {
        try {
            // Check if column exists
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet columns = metaData.getColumns(null, null, "items", "description_embedding");
            if (!columns.next()) {
                // Column doesn't exist, create it
                try (Statement stmt = connection.createStatement()) {
                    stmt.execute("""
                        ALTER TABLE items 
                        ADD COLUMN IF NOT EXISTS description_embedding TEXT
                        """);
                }
            }
        } catch (SQLException e) {
            // Column might already exist or table doesn't exist
            // Try to create it anyway
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("""
                    ALTER TABLE items 
                    ADD COLUMN IF NOT EXISTS description_embedding TEXT
                    """);
            } catch (SQLException e2) {
                // Ignore if column already exists
            }
        }
    }

    /**
     * Inner class to hold item ID and embedding
     */
    public static class ItemEmbedding {
        private final Long itemId;
        private final String embeddingStr;

        public ItemEmbedding(Long itemId, String embeddingStr) {
            this.itemId = itemId;
            this.embeddingStr = embeddingStr;
        }

        public Long getItemId() {
            return itemId;
        }

        public String getEmbeddingStr() {
            return embeddingStr;
        }
    }
}



