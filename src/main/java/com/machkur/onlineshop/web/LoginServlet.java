package com.machkur.onlineshop.web;

import com.machkur.onlineshop.entity.User;
import com.machkur.onlineshop.service.security.SecurityService;
import com.machkur.onlineshop.service.security.entity.Session;
import com.machkur.onlineshop.web.utils.PageGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class LoginServlet extends HttpServlet {
    private final SecurityService securityService;

    public LoginServlet(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PageGenerator pageGenerator = PageGenerator.instance();
        response.setStatus(HttpServletResponse.SC_OK);
        pageGenerator.writePage(response.getWriter(), "login.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            User user = User.builder().email(email).password(password).build();

            Session session = securityService.login(user);
            if (session != null) {
                Cookie cookie = new Cookie("user-token", session.getToken());
                response.addCookie(cookie);
                response.sendRedirect("/");
                return;
            }
            writeLoginFailed(response);
        } catch (IOException e) {
            writeLoginFailed(response);
        }
    }

    private void writeLoginFailed(HttpServletResponse response) throws IOException {
        PageGenerator.instance().
                writePage(response.getWriter(), "login_failed.html");
    }
}
