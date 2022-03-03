package com.machkur.onlineshop.config;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {

    private final Properties properties;

    public PropertiesReader(String path) {
        properties = new Properties();
        loadProperties(path);
    }

    public Properties getProperties() {
        return this.properties;
    }

    public void loadProperties(String path) {
        try (InputStream resource = getClass().getClassLoader()
                .getResourceAsStream(path);) {
            properties.load(resource);
        } catch (IOException e) {
            throw new RuntimeException("Cannot load properties from " + path, e);
        }
    }
}
