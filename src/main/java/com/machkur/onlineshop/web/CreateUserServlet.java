package com.machkur.onlineshop.web;

import com.machkur.onlineshop.entity.User;
import com.machkur.onlineshop.service.SecurityService;
import com.machkur.onlineshop.web.utils.PageGenerator;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collections;

public class CreateUserServlet extends HttpServlet {
    private final SecurityService securityService;

    public CreateUserServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        response.setStatus(HttpServletResponse.SC_OK);
        pageGenerator.writePage(response.getWriter(), "registration.html", Collections.emptyMap());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        try {
            String login = request.getParameter("login");
            String password = request.getParameter("password");

            User user = User.builder()
                    .login(login)
                    .password(password)
                    .build();

            securityService.createUser(user);

            response.sendRedirect("/login");
        } catch (IOException e) {
            throw new RuntimeException("Cannot redirect to /login", e);
        }
    }
}
