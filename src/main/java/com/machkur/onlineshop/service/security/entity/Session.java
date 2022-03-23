package com.machkur.onlineshop.service.security.entity;

import com.machkur.onlineshop.entity.Product;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class Session {
    private String token;
    private LocalDateTime expireDateTime;
    private List<Product> cart;
    private Role role;

    public Session(String token, Role role, LocalDateTime expireDateTime) {
        this.token = token;
        this.expireDateTime = expireDateTime;
        this.cart = new ArrayList<>();
        this.role = role;
    }
}
