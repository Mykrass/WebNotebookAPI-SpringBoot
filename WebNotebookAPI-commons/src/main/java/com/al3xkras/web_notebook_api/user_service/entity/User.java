package com.al3xkras.web_notebook_api.user_service.entity;

import com.al3xkras.web_notebook_api.user_service.model.UserDetailsProvider;
import com.al3xkras.web_notebook_api.user_service.model.UserType;
import lombok.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Collection;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "notebook_user", uniqueConstraints = {
        @UniqueConstraint(name = "user_username_un", columnNames = {"username"}),
        @UniqueConstraint(name = "user_email_un", columnNames = {"email"})
})
public class User implements Authentication {
    @Id
    @SequenceGenerator(name = "user_id_seq",initialValue = 1,allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    private Long userId;

    @Column(name = "username", nullable = false)
    private String username;
    @Column(name = "password")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "details_provider", nullable = false)
    private UserDetailsProvider detailsProvider;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    private UserType userType;

    @Transient
    private boolean authenticated;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userType.authorities();
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getDetails() {
        return detailsProvider;
    }

    @Override
    public Object getPrincipal() {
        return this;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        authenticated=isAuthenticated;
    }

    @Override
    public String getName() {
        return username;
    }
}
