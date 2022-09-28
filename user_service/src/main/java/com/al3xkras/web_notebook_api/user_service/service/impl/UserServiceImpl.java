package com.al3xkras.web_notebook_api.user_service.service.impl;

import com.al3xkras.web_notebook_api.user_service.entity.User;
import com.al3xkras.web_notebook_api.user_service.exception.UsernameExistsException;
import com.al3xkras.web_notebook_api.user_service.model.UserDetailsProvider;
import com.al3xkras.web_notebook_api.user_service.repository.UserRepository;
import com.al3xkras.web_notebook_api.user_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public User processOAuthPostLogin(String username) {
        Optional<User> existing = findByUsername(username);
        if (!existing.isPresent()){
            User user = new User();
            user.setUsername(username);
            user.setProvider(UserDetailsProvider.GOOGLE);
            log.info("Saved new user with google oAuth 2.0: "+user);
            return userRepository.saveAndFlush(user);
        }
        return existing.get();
    }

    @Override
    public Optional<User> findByEmailAddress(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) throws UsernameExistsException {
        try {
            return userRepository.saveAndFlush(user);
        } catch (DataIntegrityViolationException e){
            //TODO check which column causes exception: username or email
            throw new UsernameExistsException("username already exists");
        }
    }
}
