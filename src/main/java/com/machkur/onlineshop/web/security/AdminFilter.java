package com.machkur.onlineshop.web.security;

import com.machkur.onlineshop.service.security.SecurityService;
import com.machkur.onlineshop.service.security.entity.Role;

public class AdminFilter extends SecurityFilter{

    public AdminFilter(SecurityService securityService) {
        super(securityService);
    }

    @Override
    public boolean isRoleAllowed(Role role) {
        return Role.ADMIN.equals(role);
    }
}
