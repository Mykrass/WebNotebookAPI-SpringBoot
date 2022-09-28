package com.al3xkras.web_notebook_api.user_service.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@Builder
public class UserDTO {
    @NotNull
    @Size(min = 1, max = 15)
    @Pattern(regexp = "[a-zA-Z0-9_]{1,15}")
    private String username;
    @NotNull
    @Size(min = 8, max = 150)
    @Pattern(regexp = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[-+_!@#$%^&*.,?]).{8,}$")
    private String password;
    @Email
    private String email;
}
