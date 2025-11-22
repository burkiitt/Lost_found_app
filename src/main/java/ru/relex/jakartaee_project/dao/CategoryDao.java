package ru.relex.jakartaee_project.dao;

import ru.relex.jakartaee_project.entity.Category;
import ru.relex.jakartaee_project.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDao {

    private static final CategoryDao instance = new CategoryDao();
    private CategoryDao() {}
    public static CategoryDao getInstance() {
        return instance;
    }

    private final String SAVE_SQL = """
            INSERT INTO categories(name)
            VALUES (?)
            """;

    private final String FIND_ALL_SQL = """
            SELECT id, name FROM categories
            """;

    private final String FIND_ALL_STRING = "SELECT name FROM categories ORDER BY name";


    private final String FIND_BY_ID_SQL = FIND_ALL_SQL + " WHERE id = ?";

    private final String DELETE_SQL = "DELETE FROM categories WHERE id = ?";

private final String FIND_ID_BY_NAME_SQL = "SELECT id FROM categories WHERE name = ?";

    public Category save(Category category) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, category.getName());
            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                category.setId(rs.getLong("id"));
            }
            return category;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Category> findAll() {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)
        ) {
            List<Category> list = new ArrayList<>();
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                list.add(buildCategory(rs));
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Category findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)
        ) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return buildCategory(rs);
            }
            return null;
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

    private Category buildCategory(ResultSet rs) throws SQLException {
        return new Category(
                rs.getLong("id"),
                rs.getString("name")
        );
    }

    public List<String> findAllNames() {
        List<String> list = new ArrayList<>();

        try (Connection con = ConnectionManager.get();
             PreparedStatement ps = con.prepareStatement(FIND_ALL_STRING)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) list.add(rs.getString("name"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public long findIdByName(String name) {
        try(
                Connection connection = ConnectionManager.get();
                PreparedStatement ps = connection.prepareStatement(FIND_ID_BY_NAME_SQL)
                ){
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getLong("id");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return -1;
    }
}
