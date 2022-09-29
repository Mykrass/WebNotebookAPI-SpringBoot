package com.al3xkras.web_notebook_api.user_service.controller;

import com.al3xkras.web_notebook_api.user_service.dto.UserDTO;
import com.al3xkras.web_notebook_api.user_service.entity.User;
import com.al3xkras.web_notebook_api.user_service.exception.UsernameExistsException;
import com.al3xkras.web_notebook_api.user_service.model.UserCredentials;
import com.al3xkras.web_notebook_api.user_service.model.UserDetailsProvider;
import com.al3xkras.web_notebook_api.user_service.model.UserType;
import com.al3xkras.web_notebook_api.user_service.service.UserService;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/u")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @ExceptionHandler(UsernameExistsException.class)
    public ResponseEntity<String> handleUsernameExists(UsernameExistsException e){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserDTO userDTO){
        User user = User.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .userType(UserType.USER)
                .email(userDTO.getEmail())
                .detailsProvider(UserDetailsProvider.LOCAL)
                .build();
        User saved = userService.saveUser(user);
        log.info("saved user: "+saved);
        return ResponseEntity.ok(saved);
    }

}
