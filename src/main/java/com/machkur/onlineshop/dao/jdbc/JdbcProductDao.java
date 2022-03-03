package com.machkur.onlineshop.dao.jdbc;

import com.machkur.onlineshop.dao.ConnectionFactory;
import com.machkur.onlineshop.dao.ProductDao;
import com.machkur.onlineshop.dao.mapper.ProductRowMapper;
import com.machkur.onlineshop.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductDao implements ProductDao {

    private static final String FIND_ALL_SQL = "SELECT id, name, price, date FROM products;";
    private static final String INSERT_PRODUCT_SQL = "INSERT INTO products (name, price, date) VALUES (?, ?, ?);";
    private static final String FIND_BY_ID_SQL = "SELECT id, name, price, date FROM products WHERE id = ?;";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE products SET name = ?, price = ?;";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM products WHERE id = ?;";
    private final ConnectionFactory connectionFactory;
    private final static ProductRowMapper PRODUCT_ROW_MAPPER = new ProductRowMapper();

    public JdbcProductDao(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    @Override
    public Iterable<Product> findAll() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL_SQL);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
                productList.add(product);
            }
            return productList;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot get products", e);
        }
    }

    @Override
    public void addProduct(Product product) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCT_SQL)) {
            if (product.getName() != null && product.getPrice() >= 0.1) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setDouble(2, product.getPrice());
                preparedStatement.setDate(3, Date.valueOf(product.getCreationDate()));
                preparedStatement.executeUpdate();
            } else {
                throw new RuntimeException("Cannot create product with empty fields");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot add new product", e);
        }
    }

    @Override
    public void deleteProduct(int productId) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT_SQL)) {
            if (productId > 0) {
                preparedStatement.setInt(1, productId);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot delete product with id " + productId, e);
        }
    }

    @Override
    public void editProduct(Product product) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT_SQL)) {
            if (product != null) {
                preparedStatement.setString(1, product.getName());
                preparedStatement.setDouble(2, product.getPrice());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Cannot edit product with id " + product.getId(), e);
        }
    }

    @Override
    public Product findProductById(int productId) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            if (productId > 0) {
                preparedStatement.setInt(1, productId);
            }
            Product product = null;
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                product = PRODUCT_ROW_MAPPER.mapRow(resultSet);
            }
            return product;
        } catch (SQLException e) {
            throw new RuntimeException("Cannot find product by id " + productId, e);
        }
    }
}
