package com.machkur.onlineshop.web.controller;

import com.machkur.onlineshop.service.ProductService;
import com.machkur.onlineshop.service.security.entity.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @GetMapping("/")
    public String findAllProducts(ModelMap modelMap) {
        modelMap.addAttribute("products", productService.findAll());
        modelMap.addAttribute("userRole", Role.ADMIN);
        return "products";
    }

}
