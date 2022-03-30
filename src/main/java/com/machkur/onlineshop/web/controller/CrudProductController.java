package com.machkur.onlineshop.web.controller;

import com.machkur.onlineshop.entity.Product;
import com.machkur.onlineshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class CrudProductController {
    private final ProductService productService;
    private Logger logger = LoggerFactory.getLogger(CrudProductController.class);

    @GetMapping("/add")
    public String showAddView(ModelMap modelMap){
        modelMap.addAttribute("product", new Product());
        return "add_product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("product") Product product){
        logger.info("Product to add: {}", product);
        productService.addProduct(product);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String showEditView(ModelMap modelMap, @PathVariable int id){
        Product foundProduct = productService.findProductById(id);
        logger.info("Found product to edit: {}", foundProduct);
        modelMap.addAttribute("product", foundProduct);
        return "edit_product";
    }

    @PostMapping("/edit")
    public String editProduct(Product product){
        logger.info("Updated product: {}", product);
        productService.editProduct(product);
        return "redirect:/";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable int id){
        logger.info("Delete product with id {}", id);
        productService.deleteProduct(id);
        return "redirect:/";
    }
}
