package com.machkur.onlineshop.web;

import com.machkur.onlineshop.ServiceLocator;
import com.machkur.onlineshop.entity.Product;
import com.machkur.onlineshop.service.ProductService;
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
    private final ProductService productService = ServiceLocator.get(ProductService.class);

//    public FindAllProductsServlet(ProductService productService) {
//        this.productService = productService;
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        Session session = (Session) request.getAttribute("session");
        List<Product> productList = (List<Product>) productService.findAll();

        Map<String, Object> parametersMap = new HashMap<>();
        parametersMap.put("products", productList);
      //  parametersMap.put("userRole", session.getRole());
        response.setStatus(HttpServletResponse.SC_OK);
        pageGenerator.writePage(response.getWriter(), "products.html", parametersMap);
    }
}
