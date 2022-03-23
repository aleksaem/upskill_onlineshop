package com.machkur.onlineshop.web.security;

import com.machkur.onlineshop.service.security.SecurityService;
import com.machkur.onlineshop.service.security.entity.Role;

public class UserFilter extends SecurityFilter{

    public UserFilter(SecurityService securityService) {
        super(securityService);
    }

    @Override
    public boolean isRoleAllowed(Role role) {
        return Role.USER.equals(role);
    }
}
