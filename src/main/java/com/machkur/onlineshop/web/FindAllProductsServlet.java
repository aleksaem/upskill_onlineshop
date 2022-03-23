package com.machkur.onlineshop.web;

import com.machkur.onlineshop.entity.Product;
import com.machkur.onlineshop.service.ProductService;
import com.machkur.onlineshop.service.security.entity.Role;
import com.machkur.onlineshop.service.security.entity.Session;
import com.machkur.onlineshop.web.utils.PageGenerator;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindAllProductsServlet extends HttpServlet {
    private final ProductService productService;

    public FindAllProductsServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        Session session = getSession(request);
        List<Product> productList = (List<Product>) productService.findAll();

        Map<String, Object> parametersMap = new HashMap<>();
        Role role = (session != null) ? session.getRole() : Role.GUEST;
        parametersMap.put("products", productList);
        parametersMap.put("userRole", role);
        response.setStatus(HttpServletResponse.SC_OK);
        pageGenerator.writePage(response.getWriter(), "products.html", parametersMap);
    }

    private Session getSession(HttpServletRequest request) {
        return (Session) request.getAttribute("session");
    }
}
