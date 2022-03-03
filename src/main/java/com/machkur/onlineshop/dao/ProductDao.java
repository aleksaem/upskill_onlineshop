package com.machkur.onlineshop.dao;

import com.machkur.onlineshop.entity.Product;

public interface ProductDao {

    Iterable<Product> findAll();

    void addProduct(Product product);

    void deleteProduct(int productId);

    void editProduct(Product product);

    Product findProductById(int productId);
}
