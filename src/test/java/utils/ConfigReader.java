package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties properties;

    static {
        try {
            String filePath = "src/test/resources/Config.properties";
            FileInputStream inputStream = new FileInputStream(filePath);
            properties = new Properties();
            properties.load(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Config.properties file not found or cannot be loaded!");
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
