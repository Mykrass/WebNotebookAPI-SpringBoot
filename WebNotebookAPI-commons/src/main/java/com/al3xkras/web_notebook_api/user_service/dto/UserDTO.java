package com.al3xkras.web_notebook_api.user_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String username;
    private String password;
    private String email;
}
