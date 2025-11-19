package ru.relex.jakartaee_project.dao;

import ru.relex.jakartaee_project.entity.Role;
import ru.relex.jakartaee_project.entity.User;
import ru.relex.jakartaee_project.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDao {

    private static final UserDao instance = new UserDao();
    private UserDao() {}
    public static UserDao getInstance() {
        return instance;
    }

    private final String SAVE_SQL = """
            INSERT INTO users(full_name, email, password_hash, role)
            VALUES (?, ?, ?, ?)
            """;

    private final String FIND_BY_ID_SQL = """
            SELECT id, full_name, email, password_hash, role, created_at 
            FROM users WHERE id = ?
            """;

    private final String FIND_BY_EMAIL_SQL = """
            SELECT id, full_name, email, password_hash, role, created_at 
            FROM users WHERE email = ?
            """;

    private final String FIND_ALL_SQL = """
            SELECT id, full_name, email, password_hash, role, created_at 
            FROM users
            """;

    private final String DELETE_SQL = "DELETE FROM users WHERE id = ?";

    private final String FIND_BY_EMAIL_PASSWORD= """
            select * from users where email = ? and password_hash = ?
            """;

    public User save(User user) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)
        ) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPassword());
            statement.setString(4, user.getRole().name());

            statement.executeUpdate();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                user.setId(rs.getLong(1 ));
            }

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(Long id) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)
        ) {
            statement.setLong(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) return buildUser(rs);
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findByEmail(String email) {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_EMAIL_SQL)
        ) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) return buildUser(rs);
            return null;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> findAll() {
        try (Connection connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)
        ) {
            ResultSet rs = statement.executeQuery();

            List<User> list = new ArrayList<>();
            while (rs.next()) list.add(buildUser(rs));

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

    private User buildUser(ResultSet rs) throws SQLException {
        return new User(
                rs.getLong("id"),
                rs.getString("full_name"),
                rs.getString("email"),
                rs.getString("password_hash"),
                Role.USER,
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
    public User findByEmailPassword(String email, String password) {
        try(
                Connection connection = ConnectionManager.get();
                var statement = connection.prepareStatement(FIND_BY_EMAIL_PASSWORD)
        ){
            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) return buildUser(rs);
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
