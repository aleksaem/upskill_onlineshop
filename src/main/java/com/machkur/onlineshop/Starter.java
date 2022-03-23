package com.machkur.onlineshop;

import com.machkur.onlineshop.config.PropertiesReader;
import com.machkur.onlineshop.dao.ProductDao;
import com.machkur.onlineshop.dao.UserDao;
import com.machkur.onlineshop.dao.jdbc.JdbcProductDao;
import com.machkur.onlineshop.dao.jdbc.JdbcUserDao;
import com.machkur.onlineshop.service.CartService;
import com.machkur.onlineshop.service.ProductService;
import com.machkur.onlineshop.service.security.SecurityService;
import com.machkur.onlineshop.service.UserService;
import com.machkur.onlineshop.web.*;
import com.machkur.onlineshop.web.security.AdminFilter;
import com.machkur.onlineshop.web.security.SecurityFilter;
import com.machkur.onlineshop.web.security.SessionFilter;
import com.machkur.onlineshop.web.security.UserFilter;
import jakarta.servlet.DispatcherType;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.postgresql.ds.PGSimpleDataSource;

import java.util.EnumSet;
import java.util.Properties;

public class Starter {

    public static void main(String[] args) throws Exception {
        //configuration
        PropertiesReader propertiesReader = new PropertiesReader("application.properties");
        Properties properties = propertiesReader.getProperties();
        PGSimpleDataSource connectionFactory = new PGSimpleDataSource();
        connectionFactory.setUrl(properties.getProperty("db.url"));
        connectionFactory.setUser(properties.getProperty("db.username"));
        connectionFactory.setPassword(properties.getProperty("db.password"));

        //dao
        ProductDao productDao = new JdbcProductDao(connectionFactory);
        UserDao userDao = new JdbcUserDao(connectionFactory);

        //service
        ProductService productService = new ProductService(productDao);
        UserService userService = new UserService(userDao);
        SecurityService securityService = new SecurityService(userService);
        CartService cartService = new CartService(securityService, productService);

        //servlets
        FindAllProductsServlet findAllProductsServlet = new FindAllProductsServlet(productService);
        AddProductServlet addProductServlet = new AddProductServlet(productService);
        DeleteProductServlet deleteProductServlet = new DeleteProductServlet(productService);
        EditProductServlet editProductServlet = new EditProductServlet(productService);
        CartServlet cartServlet = new CartServlet(cartService);
        AddProductToCartServlet addProductToCartServlet = new AddProductToCartServlet(cartService);
        DeleteProductFromCartServlet deleteProductFromCartServlet = new DeleteProductFromCartServlet(cartService);

        RegistrationServlet registrationServlet = new RegistrationServlet(securityService);
        LoginServlet loginServlet = new LoginServlet(securityService);


        //mapping
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.addServlet(new ServletHolder(findAllProductsServlet), "/");
        contextHandler.addServlet(new ServletHolder(addProductServlet), "/products/add");
        contextHandler.addServlet(new ServletHolder(deleteProductServlet), "/products/delete");
        contextHandler.addServlet(new ServletHolder(editProductServlet), "/products/edit");
        contextHandler.addServlet(new ServletHolder(cartServlet), "/cart");
        contextHandler.addServlet(new ServletHolder(addProductToCartServlet), "/cart/add");
        contextHandler.addServlet(new ServletHolder(deleteProductFromCartServlet), "/cart/delete");
        contextHandler.addServlet(new ServletHolder(registrationServlet), "/registration");
        contextHandler.addServlet(new ServletHolder(loginServlet), "/login");


        //filter
        AdminFilter adminFilter = new AdminFilter(securityService);
        UserFilter userFilter = new UserFilter(securityService);
        SessionFilter sessionFilter = new SessionFilter(securityService);


        contextHandler.addFilter(new FilterHolder(adminFilter), "/products/*", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter(new FilterHolder(userFilter), "/cart", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter(new FilterHolder(userFilter), "/cart/*", EnumSet.of(DispatcherType.REQUEST));
        contextHandler.addFilter(new FilterHolder(sessionFilter), "/*", EnumSet.of(DispatcherType.REQUEST));

        Server server = new Server(8080);
        server.setHandler(contextHandler);

        server.start();
    }
}
