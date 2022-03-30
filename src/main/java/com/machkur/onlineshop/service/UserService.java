package com.machkur.onlineshop.service;

import com.machkur.onlineshop.dao.UserDao;
import com.machkur.onlineshop.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

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
