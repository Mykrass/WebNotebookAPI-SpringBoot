package com.al3xkras.web_notebook_api.user_service.model;


import com.al3xkras.web_notebook_api.user_service.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class NotebookUserAuthenticationManager implements AuthenticationManager {

    private final UserService userService;

    public NotebookUserAuthenticationManager(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return null;
    }
}
