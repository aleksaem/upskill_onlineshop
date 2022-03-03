package com.machkur.onlineshop.dao.mapper;

import com.machkur.onlineshop.entity.Product;
import org.junit.jupiter.api.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductRowMapperTest {

    @Test
    void mapRow() throws SQLException {
        LocalDate creationDate = LocalDate.now();
        Product expectedProduct = Product.builder()
                .id(1)
                .name("Apple")
                .price(10.99)
                .creationDate(creationDate)
                .build();

        ProductRowMapper productRowMapper = new ProductRowMapper();

        ResultSet resultSet = mock(ResultSet.class);
        when(resultSet.getInt("id")).thenReturn(1);
        when(resultSet.getDouble("price")).thenReturn(10.99);
        when(resultSet.getString("name")).thenReturn("Apple");
        when(resultSet.getDate("date")).thenReturn(Date.valueOf(creationDate));

        Product actualProduct = productRowMapper.mapRow(resultSet);

        assertNotNull(actualProduct);
        assertEquals(expectedProduct, actualProduct);

    }
}