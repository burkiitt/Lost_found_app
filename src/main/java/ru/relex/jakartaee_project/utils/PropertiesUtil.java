package ru.relex.jakartaee_project.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {
    private static final Properties properties = new Properties();
    private PropertiesUtil() {}
    static {
        loadproperties();
    }

    private static void loadproperties() {
        var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
        try{
            properties.load(inputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
