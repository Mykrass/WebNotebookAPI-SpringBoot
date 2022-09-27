package com.al3xkras.web_notebook_api.user_service.model.impl;

import com.al3xkras.web_notebook_api.user_service.model.UserCredentials;

public class NotebookUserCredentials implements UserCredentials {
    private final String username;
    private final String password;

    public NotebookUserCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return username;
    }
}
