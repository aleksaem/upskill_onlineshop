package com.machkur.onlineshop.dao;

import com.machkur.onlineshop.entity.User;

public interface UserDao {

    void addUser(User user);

    User findUserByEmailAndPassword(String email, String password);

    User findUserByEmail(String email);
}
