package com.machkur.onlineshop.web.security;

import com.machkur.onlineshop.service.security.SecurityService;
import com.machkur.onlineshop.service.security.entity.Role;
import com.machkur.onlineshop.service.security.entity.Session;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public abstract class SecurityFilter implements Filter {
    private final SecurityService securityService;

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie[] cookies = request.getCookies();
        Session session = getSession(cookies);

        if (session != null && isRoleAllowed(session.getRole())) {
            request.setAttribute("session", session);
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            response.sendRedirect("/login");
        }
    }

    public abstract boolean isRoleAllowed(Role role);

    private Session getSession(Cookie[] cookies) {
       return securityService.getSession(getToken(cookies));
    }

    private String getToken(Cookie[] cookies) {
        Optional<Cookie> token = Arrays.stream(cookies)
                .filter(cookie -> "user-token".equals(cookie.getName()))
                .filter(cookie -> securityService.isTokenValid(cookie.getValue())).findAny();

        return token.map(Cookie::getValue).orElse(null);
    }
}
