package com.al3xkras.web_notebook_api.user_service.service;

import com.al3xkras.web_notebook_api.user_service.entity.User;
import com.al3xkras.web_notebook_api.user_service.exception.UsernameExistsException;

import java.util.Optional;

public interface UserService {
    User processOAuthPostLogin(String email);
    Optional<User> findByEmailAddress(String email);
    Optional<User> findByUsername(String username);

    User saveUser(User user) throws UsernameExistsException;
}
