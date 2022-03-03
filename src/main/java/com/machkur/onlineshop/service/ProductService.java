package com.machkur.onlineshop.service;

import com.machkur.onlineshop.dao.ProductDao;
import com.machkur.onlineshop.entity.Product;

import java.time.LocalDate;

public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Iterable<Product> findAll() {
        return productDao.findAll();
    }

    public void addProduct(Product product) {
        LocalDate creationDate = LocalDate.now();
        product.setCreationDate(creationDate);
        productDao.addProduct(product);
    }

    public void deleteProduct(int productId) {
        productDao.deleteProduct(productId);
    }

    public void editProduct(Product product) {
        productDao.editProduct(product);
    }

    public Product findProductById(int productId) {
        return productDao.findProductById(productId);
    }
}
