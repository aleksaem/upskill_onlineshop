package com.machkur.onlineshop.service;

import com.machkur.onlineshop.dao.UserDao;
import com.machkur.onlineshop.entity.User;

import java.time.LocalDate;
import java.util.List;

public class SecurityService {
    private final UserDao userDao;

    public SecurityService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void createUser(User user) {
        List<User> existentUsers = userDao.findAllUsers();
        boolean isUnique = true;

        for (User existentUser : existentUsers) {
            if (existentUser.getLogin().equals(user.getLogin())) {
                isUnique = false;
                break;
            }
        }

        if (isUnique) {
            LocalDate creationDate = LocalDate.now();
            user.setCreationDate(creationDate);
            userDao.addUser(user);
        } else {
            throw new RuntimeException("Login must be unique");
        }
    }
}
