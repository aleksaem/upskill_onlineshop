package com.machkur.onlineshop.web.security;

import com.machkur.onlineshop.service.security.SecurityService;
import com.machkur.onlineshop.service.security.entity.Session;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class SessionFilter implements Filter {
    private final SecurityService securityService;

    public SessionFilter(SecurityService securityService) {
        this.securityService = securityService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        Cookie[] cookies = request.getCookies();
        Session session = securityService.getSession(getToken(cookies));

        if(session != null){
            request.setAttribute("session", session);
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getToken(Cookie[] cookies) {
        Optional<Cookie> token = Arrays.stream(cookies)
                .filter(cookie -> "user-token".equals(cookie.getName()))
                .filter(cookie -> securityService.isTokenValid(cookie.getValue())).findAny();

        return token.map(Cookie::getValue).orElse(null);
    }
}
