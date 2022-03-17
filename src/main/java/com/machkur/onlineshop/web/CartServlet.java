package com.machkur.onlineshop.web;

import com.machkur.onlineshop.entity.Product;
import com.machkur.onlineshop.service.CartService;
import com.machkur.onlineshop.service.security.entity.Session;
import com.machkur.onlineshop.web.utils.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartServlet extends HttpServlet {
    private final CartService cartService;

    public CartServlet(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        Session session = (Session) request.getAttribute("session");
        List<Product> productList = cartService.findAllProductsAtCart(session);

        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("products", productList);
        response.setStatus(HttpServletResponse.SC_OK);
        pageGenerator.writePage(response.getWriter(), "user_cart.html", parametersMap);
    }
}
