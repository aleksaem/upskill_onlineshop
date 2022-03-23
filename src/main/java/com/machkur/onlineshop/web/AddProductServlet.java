package com.machkur.onlineshop.web;

import com.machkur.onlineshop.entity.Product;
import com.machkur.onlineshop.service.ProductService;
import com.machkur.onlineshop.web.utils.PageGenerator;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddProductServlet extends HttpServlet {
    private final ProductService productService;

    public AddProductServlet(ProductService productService) {
        this.productService = productService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        response.setStatus(HttpServletResponse.SC_OK);
        pageGenerator.writePage(response.getWriter(),"add_product.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));

            Product product = Product.builder()
                    .name(name)
                    .price(price)
                    .build();

            productService.addProduct(product);
            response.sendRedirect("/");
        } catch (IOException e) {
            throw new RuntimeException("Cannot redirect to /products", e);
        }
    }
}
