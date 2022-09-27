package com.al3xkras.web_notebook_api.user_service;

import org.springframework.security.core.GrantedAuthority;

public enum UserAuthority implements GrantedAuthority {
    ;

    @Override
    public String getAuthority() {
        return this.name();
    }
}
