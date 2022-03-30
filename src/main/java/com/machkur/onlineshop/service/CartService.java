package com.machkur.onlineshop.service;

import com.machkur.onlineshop.entity.Product;
import com.machkur.onlineshop.service.security.entity.Session;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductService productService;

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
