package ru.relex.jakartaee_project.utils;

import javax.swing.*;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionManager {
    private static final String url_key = "db.url";
    private static final String user_key = "db.username";
    private static final String password_key = "db.password";
    private static final int Default_Poll_Size = 10;
    private static final String Pool_Size_Key = "db.poll.size";
    private static BlockingQueue<Connection> pool;

    static {
        loadDriver();
        initConnectionPool();
    }

    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void initConnectionPool() {
        String pool_size = PropertiesUtil.get(Pool_Size_Key);
        int size = pool_size == null ? Default_Poll_Size : Integer.parseInt(pool_size);
        pool = new ArrayBlockingQueue<>(size);

        for (int i = 0; i < size; i++) {
            Connection connection = getConnection();
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(
                    ConnectionManager.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> {
                        if ("close".equals(method.getName())) {
                            pool.put((Connection) proxy);
                            return null;
                        }
                        return method.invoke(connection, args);
                    }
            );
            pool.add(proxyConnection);
        }
    }


    public static Connection get (){
        try {
            return pool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager(){

    }

    private static Connection getConnection() {
        try {
            return DriverManager.getConnection(PropertiesUtil.get(url_key),
                    PropertiesUtil.get(user_key),
                    PropertiesUtil.get(password_key));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
