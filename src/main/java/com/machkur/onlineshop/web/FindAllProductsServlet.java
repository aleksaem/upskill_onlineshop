package com.machkur.onlineshop.web;

import com.machkur.onlineshop.entity.Product;
import com.machkur.onlineshop.service.ProductService;
import com.machkur.onlineshop.web.utils.PageGenerator;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
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
        List<Product> productList = (List<Product>) productService.findAll();

        Map<String, List<Product>> parametersMap = new HashMap<>();
        parametersMap.put("products", productList);
        response.setStatus(HttpServletResponse.SC_OK);
        pageGenerator.writePage(response.getWriter(), "products.html", parametersMap);
    }
}
