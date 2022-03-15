package com.machkur.onlineshop.service;

import com.machkur.onlineshop.dao.UserDao;
import com.machkur.onlineshop.entity.User;

import java.time.LocalDate;

public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void addUser(User user){
        LocalDate creationDate = LocalDate.now();
        user.setCreationDate(creationDate);
        userDao.addUser(user);
    }

    public User findUserByEmailAndPassword(String email, String password){
        return userDao.findUserByEmailAndPassword(email, password);
    }

    public User findUserByEmail(String email){
        return userDao.findUserByEmail(email);
    }
}
