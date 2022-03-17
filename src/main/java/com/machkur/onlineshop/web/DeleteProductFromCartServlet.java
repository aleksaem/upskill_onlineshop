package com.machkur.onlineshop.web;

import com.machkur.onlineshop.service.CartService;
import com.machkur.onlineshop.service.security.entity.Session;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class DeleteProductFromCartServlet extends HttpServlet {
    private final CartService cartService;

    public DeleteProductFromCartServlet(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            Session session = (Session) request.getAttribute("session");
            int id = Integer.parseInt(request.getParameter("id"));

            cartService.deleteFromCart(id, session);
            response.sendRedirect("/products/cart");
        } catch (IOException e) {
            throw new RuntimeException("Cannot redirect to /products/cart", e);
        }
    }
}
