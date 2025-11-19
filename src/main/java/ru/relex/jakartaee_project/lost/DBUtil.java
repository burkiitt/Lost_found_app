package ru.relex.jakartaee_project.lost;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {
    private static final String URL = "jdbc:postgresql://localhost:5432/Lost_found";
    private static final String USER = "postgres";      // замени, если у тебя другой
    private static final String PASSWORD = "Admin";      // свой пароль

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}