package com.machkur.onlineshop.dao;

import com.machkur.onlineshop.entity.User;

import java.util.List;

public interface UserDao {

    void addUser(User user);

    User findUserByLoginAndPassword(String login, String password);

    List<User> findAllUsers();
}
