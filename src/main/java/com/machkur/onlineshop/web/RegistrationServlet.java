package com.machkur.onlineshop.web;

import com.machkur.onlineshop.entity.User;
import com.machkur.onlineshop.service.security.SecurityService;
import com.machkur.onlineshop.service.security.entity.Session;
import com.machkur.onlineshop.web.utils.PageGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

public class RegistrationServlet extends HttpServlet {
    private final SecurityService securityService;

    public RegistrationServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        response.setStatus(HttpServletResponse.SC_OK);
        pageGenerator.writePage(response.getWriter(), "registration.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            User user = User.builder().email(email).password(password).build();

            Optional<Session> optionalSession = securityService.register(user);
            if (optionalSession.isPresent()) {
                addCookieAndRedirect(response, optionalSession.get());
            } else {
                redirectToRegistration(response);
            }
        } catch (IOException e) {
            redirectToRegistration(response);
        }
    }

    private void redirectToRegistration(HttpServletResponse response) throws IOException {
        response.sendRedirect("/registration");
    }

    @SneakyThrows
    private void addCookieAndRedirect(HttpServletResponse response, Session session) {
        Cookie cookie = new Cookie("user-token", session.getToken());
        response.addCookie(cookie);
        response.sendRedirect("/");
    }
}
