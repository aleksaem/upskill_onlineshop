package com.machkur.onlineshop.service;

import com.machkur.onlineshop.dao.ProductDao;
import com.machkur.onlineshop.entity.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    ProductDao productDao = mock(ProductDao.class);
    ProductService productService = new ProductService(productDao);

    @DisplayName("Test find all products executed successfully")
    @Test
    public void testFindAll() {
        Product firstProduct = Product.builder().id(1).name("Apple").price(12.57).creationDate(LocalDate.now()).build();
        Product secondProduct = Product.builder().id(2).name("Strawberry").price(18.99).creationDate(LocalDate.now()).build();
        List<Product> productList = List.of(firstProduct, secondProduct);

        when(productService.findAll()).thenReturn(productList);

        List<Product> actualProducts = (List<Product>) productService.findAll();

        assertNotNull(actualProducts);
        assertEquals(2, actualProducts.size());
        assertEquals(18.99, actualProducts.get(1).getPrice());

        verify(productDao, times(1)).findAll();
    }

    @DisplayName("Test findProductById returns correct value")
    @Test
    public void testFindProductById() {
        Product product = Product.builder().id(1).name("Apple").price(12.57).creationDate(LocalDate.now()).build();

        when(productService.findProductById(1)).thenReturn(product);
        Product actualProduct = productService.findProductById(1);

        assertNotNull(actualProduct);
        assertEquals(product, actualProduct);

        verify(productDao, times(1)).findProductById(1);
    }

    @DisplayName("Test add product executed successfully")
    @Test
    public void testAddProduct(){
        Product product = Product.builder().id(1).name("Apple").price(12.57).creationDate(LocalDate.now()).build();

        productService.addProduct(product);
        verify(productDao, times(1)).addProduct(product);
    }

    @DisplayName("Test edit product executed successfully")
    @Test
    public void testEditProduct(){
        Product productToEdit = Product.builder().id(1).name("Apple").price(12.57).creationDate(LocalDate.now()).build();

        productService.editProduct(productToEdit);
        verify(productDao, times(1)).editProduct(productToEdit);
    }

    @DisplayName("Test delete product executed successfully")
    @Test
    public void testDeleteProduct(){
        Product productToEdit = Product.builder().id(1).name("Apple").price(12.57).creationDate(LocalDate.now()).build();

        productService.deleteProduct(1);
        verify(productDao, times(1)).deleteProduct(1);
    }
}