package com.machkur.onlineshop.dao.mapper;

import com.machkur.onlineshop.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper {

    public User mapRow(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getInt("id"))
                .login(resultSet.getString("login"))
                .password(resultSet.getString("password"))
                .creationDate(resultSet.getDate("date").toLocalDate())
                .build();
    }
}
