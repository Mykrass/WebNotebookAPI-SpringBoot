package com.al3xkras.web_notebook_api.user_service.entity;

import com.al3xkras.web_notebook_api.user_service.model.UserDetailsProvider;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "user", uniqueConstraints = {
        @UniqueConstraint(name = "user_username_un", columnNames = {"username"}),
        @UniqueConstraint(name = "user_email_un", columnNames = {"email"})
})
public class User {
    @Id
    private long userId;

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "provider")
    private UserDetailsProvider provider;

    @Column(name = "email")
    private String email;

}
