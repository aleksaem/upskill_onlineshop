package com.machkur.onlineshop.web.security;

import com.machkur.onlineshop.service.SecurityService;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;

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
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Cookie[] cookies = httpServletRequest.getCookies();

        boolean isAuthorized = (cookies != null) && isTokenValid(cookies);
        if(!isAuthorized){
            httpServletResponse.sendRedirect("/login");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isTokenValid(Cookie[] cookies){
        return Arrays.stream(cookies)
                .filter(cookie -> "user-token".equals(cookie.getName()))
                .anyMatch(cookie -> securityService.isTokenValid(cookie.getValue()));
    }

    @Override
    public void destroy() {

    }
}
