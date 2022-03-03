package com.machkur.onlineshop;

import com.machkur.onlineshop.config.PropertiesReader;
import com.machkur.onlineshop.dao.ConnectionFactory;
import com.machkur.onlineshop.dao.ProductDao;
import com.machkur.onlineshop.dao.UserDao;
import com.machkur.onlineshop.dao.jdbc.JdbcProductDao;
import com.machkur.onlineshop.dao.jdbc.JdbcUserDao;
import com.machkur.onlineshop.service.ProductService;
import com.machkur.onlineshop.service.SecurityService;
import com.machkur.onlineshop.web.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import java.util.Properties;

public class Starter {

    public static void main(String[] args) throws Exception {

        PropertiesReader propertiesReader = new PropertiesReader(
                "application.properties");
        Properties properties = propertiesReader.getProperties();

        ConnectionFactory connectionFactory = new ConnectionFactory(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password"));

        ProductDao productDao = new JdbcProductDao(connectionFactory);
        UserDao userDao = new JdbcUserDao(connectionFactory);

        ProductService productService = new ProductService(productDao);
        SecurityService securityService = new SecurityService(userDao);

        FindAllProductsServlet findAllProductsServlet = new FindAllProductsServlet(productService);
        AddProductServlet addProductServlet = new AddProductServlet(productService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService);
        EditProductServlet editProductServlet = new EditProductServlet(productService);

        CreateUserServlet createUserServlet = new CreateUserServlet(securityService);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(findAllProductsServlet), "/");
        contextHandler.addServlet(new ServletHolder(addProductServlet), "/products/add");
        contextHandler.addServlet(new ServletHolder(deleteProductServlet), "/products/delete");
        contextHandler.addServlet(new ServletHolder(editProductServlet), "/products/edit");
        contextHandler.addServlet(new ServletHolder(createUserServlet), "/registration");

        Server server = new Server(8081);
        server.setHandler(contextHandler);

        server.start();
    }
}
