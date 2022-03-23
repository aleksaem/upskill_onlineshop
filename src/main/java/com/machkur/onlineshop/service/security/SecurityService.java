package com.machkur.onlineshop.service.security;

import com.machkur.onlineshop.entity.User;
import com.machkur.onlineshop.service.UserService;
import com.machkur.onlineshop.service.security.entity.Role;
import com.machkur.onlineshop.service.security.entity.Session;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SecurityService {
    private final UserService userService;
    private final List<Session> sessions;

    public SecurityService(UserService userService) {
        this.userService = userService;
        this.sessions = new ArrayList<>();
    }

    public Optional<Session> register(User user) throws IOException {
        if (userService.findUserByEmail(user.getEmail()) != null) {
            return Optional.empty();
        }
        String salt = generateUUID();
        user.setSalt(salt);
        user.setRole(Role.USER.toString());
        user.setPassword(encode(user.getPassword(), salt));
        userService.addUser(user);
        String token = generateToken();
        return Optional.of(createSession(user, token));

    }

    public Session login(User user) throws IOException {
        //checkSessions();
        Session session = null;
        User foundUser = userService.findUserByEmail(user.getEmail());
        if (foundUser != null) {
            String encodedPassword = encode(user.getPassword(), foundUser.getSalt());
            String currentPassword = foundUser.getPassword();
            if (currentPassword.equals(encodedPassword)) {
                String token = generateToken();
                session = createSession(foundUser, token);
            }
        }
        return session;
    }

    private String generateUUID() {
        return UUID.randomUUID().toString();
    }

    private String encode(String password, String salt) {
        return DigestUtils.sha256Hex((password + salt).getBytes(StandardCharsets.UTF_8));
    }

    private String generateToken() {
        return generateUUID();
    }

    private Session createSession(User user, String token) {
        Role userRole = Role.valueOf(user.getRole().toUpperCase());
        LocalDateTime expireDateTime = LocalDateTime.now().plusMinutes(60);
        Session session = new Session(token, userRole, expireDateTime);
        sessions.add(session);
        return session;
    }

    private void checkSessions() {
        Stream<Session> expiredSessions = sessions.stream()
                .filter(session -> session.getExpireDateTime().isBefore(LocalDateTime.now()));
        List<Session> expiredSessionsList = expiredSessions.collect(Collectors.toList());
        sessions.removeAll(expiredSessionsList);
    }

    public boolean isTokenValid(String token) {
        return sessions.stream()
                .filter(session -> session.getToken().equals(token))
                .anyMatch(session -> session.getExpireDateTime().isAfter(LocalDateTime.now()));
    }

    public Session getSession(String token) {
        Optional<Session> optionalSession = sessions.stream().filter(session -> session.getToken().equals(token)).findAny();
        return optionalSession.orElse(null);
    }
}
