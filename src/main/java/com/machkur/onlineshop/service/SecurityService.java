package com.machkur.onlineshop.service;

import com.machkur.onlineshop.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class SecurityService {
    private final UserService userService;
    private final List<String> tokens;

    public SecurityService(UserService userService) {
        this.userService = userService;
        this.tokens = new ArrayList<>();
    }

    public String register(User user) throws IOException {
        String token = null;
        if (userService.findUserByEmail(user.getEmail()) == null) {
            String salt = generateUUID();
            user.setSalt(salt);
            user.setPassword(encode(user.getPassword(), salt));
            userService.addUser(user);
            token = generateToken();
        }

        return token;
    }

    public String login(User user) throws IOException {
        String token = null;
        User foundUser = userService.findUserByEmail(user.getEmail());
        if (foundUser != null) {
            String encodedPassword = encode(user.getPassword(), foundUser.getSalt()).trim();
            String currentPassword = foundUser.getPassword().trim();
            if (currentPassword.equals(encodedPassword)) {
                token = generateToken();
            }
        }
        return token;
    }

//    private boolean isUserExists(User user) {
//        String email = user.getEmail();
//        String password = user.getPassword();
//        User foundUser = userService.findUserByEmailAndPassword(email, password);
//
//        if (foundUser == null) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    private String encode(String password, String salt) {
        return DigestUtils.sha256Hex((password + salt).getBytes(StandardCharsets.UTF_8));
    }

    private String generateToken() {
        String token = generateUUID();
        tokens.add(token);
        return token;
    }

    public boolean isTokenValid(String value){
        return tokens.contains(value);
    }
}
