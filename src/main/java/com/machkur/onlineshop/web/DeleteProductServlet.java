package com.machkur.onlineshop.web;

import com.machkur.onlineshop.service.ProductService;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteProductServlet extends HttpServlet {
    private final ProductService productService;

    public DeleteProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            productService.deleteProduct(id);

            response.sendRedirect("/products");
        } catch (IOException e) {
            throw new RuntimeException("Cannot redirect to /products", e);
        }
    }
}
