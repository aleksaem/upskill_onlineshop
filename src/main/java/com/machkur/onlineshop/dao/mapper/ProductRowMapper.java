package com.machkur.onlineshop.dao.mapper;

import com.machkur.onlineshop.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductRowMapper {

    public Product mapRow(ResultSet resultSet) throws SQLException {
        return Product.builder()
                .id(resultSet.getInt("id"))
                .price(resultSet.getDouble("price"))
                .name(resultSet.getString("name"))
                .creationDate((resultSet.getDate("date")).toLocalDate())
                .build();
    }
}
