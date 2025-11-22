package ru.relex.jakartaee_project.dao;

import ru.relex.jakartaee_project.entity.Image;
import ru.relex.jakartaee_project.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ImageDao {

    private static final ImageDao instance = new ImageDao();
    private ImageDao() {}
    public static ImageDao getInstance() {
        return instance;
    }

    private final String SAVE_SQL = """
            INSERT INTO images(item_id, image_url)
            VALUES (?, ?)
            """;

    private final String FIND_BY_ITEM_SQL = """
            SELECT id, item_id, image_url FROM images
            WHERE item_id = ?
            """;

    private final String DELETE_SQL = "DELETE FROM images WHERE id = ?";

    public Image save(Image image) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, image.getItem_id());
            statement.setString(2, image.getUrl());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                image.setId(rs.getLong("id"));
            }

            return image;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public Long save_return_id(Image image) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setLong(1, image.getItem_id());
            statement.setString(2, image.getUrl());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                return rs.getLong("id");
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1L;
    }

    public List<Image> findByItemID(Long itemId) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ITEM_SQL)
        ) {
            statement.setLong(1, itemId);
            ResultSet rs = statement.executeQuery();

            List<Image> list = new ArrayList<>();
            while (rs.next()) {
                list.add(buildImage(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean delete(Long id) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)
        ) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Image buildImage(ResultSet rs) throws SQLException {
        return new Image(
                rs.getLong("id"),
                rs.getLong("item_id"),
                rs.getString("image_url")
        );
    }
}
