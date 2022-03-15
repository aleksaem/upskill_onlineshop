package com.machkur.onlineshop.dao.mapper;

import com.machkur.onlineshop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {

    public User mapRow(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .salt(resultSet.getString("salt"))
                .role(resultSet.getString("role"))
                .creationDate(resultSet.getDate("date").toLocalDate())
                .build();
    }
}
