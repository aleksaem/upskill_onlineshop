package com.machkur.onlineshop;

import com.machkur.onlineshop.config.PropertiesReader;
import com.machkur.onlineshop.dao.ProductDao;
import com.machkur.onlineshop.dao.jdbc.JdbcProductDao;
import com.machkur.onlineshop.service.ProductService;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ServiceLocator {
    private static final Map<Class<?>, Object> CONTEXT = new HashMap<>();

    static {
        PropertiesReader propertiesReader = new PropertiesReader("application.properties");
        Properties properties = propertiesReader.getProperties();

        PGSimpleDataSource connectionFactory = new PGSimpleDataSource();
        connectionFactory.setUrl(properties.getProperty("db.url"));
        connectionFactory.setUser(properties.getProperty("db.username"));
        connectionFactory.setPassword(properties.getProperty("db.password"));

        ProductDao productDao = new JdbcProductDao(connectionFactory);

        ProductService productService = new ProductService(productDao);

        CONTEXT.put(ProductService.class, productService);
    }

    public static <T> T get(Class<T> clazz){
        return clazz.cast(CONTEXT.get(clazz));
    }
}
