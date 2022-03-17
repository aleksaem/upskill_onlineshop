package com.machkur.onlineshop.service;

import com.machkur.onlineshop.entity.Product;
import com.machkur.onlineshop.service.security.SecurityService;
import com.machkur.onlineshop.service.security.entity.Session;

import java.util.List;

public class CartService {
    private final SecurityService securityService;
    private final ProductService productService;

    public CartService(SecurityService securityService, ProductService productService) {
        this.securityService = securityService;
        this.productService = productService;
    }

    public void addToCart(int productId, Session session){
        Product product = productService.findProductById(productId);
        session.getCart().add(product);
    }

    public void deleteFromCart(int productId, Session session){
        Product product = productService.findProductById(productId);
        session.getCart().remove(product);
    }

    public List<Product> findAllProductsAtCart(Session session){
        return session.getCart();
    }
}
