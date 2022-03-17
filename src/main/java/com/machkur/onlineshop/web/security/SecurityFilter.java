package com.machkur.onlineshop.web.security;

import com.machkur.onlineshop.service.security.SecurityService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class SecurityFilter implements Filter {
    private final SecurityService securityService;

    public SecurityFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Cookie[] cookies = request.getCookies();
        String token = getToken(cookies);
        boolean isAuthorized = (cookies != null) && (token != null);
        if (!isAuthorized) {
            response.sendRedirect("/login");
        } else {
            request.setAttribute("session", securityService.getSession(token));
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private String getToken(Cookie[] cookies) {
        Optional<Cookie> token = Arrays.stream(cookies)
                .filter(cookie -> "user-token".equals(cookie.getName()))
                .filter(cookie -> securityService.isTokenValid(cookie.getValue())).findAny();

        return token.map(Cookie::getValue).orElse(null);
    }

    @Override
    public void destroy() {

    }
}
