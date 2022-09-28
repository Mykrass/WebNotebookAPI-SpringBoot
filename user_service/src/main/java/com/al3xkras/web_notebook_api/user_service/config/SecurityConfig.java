package com.al3xkras.web_notebook_api.user_service.config;

import com.al3xkras.web_notebook_api.user_service.filter.NotebookUserAuthenticationFilter;
import com.al3xkras.web_notebook_api.user_service.filter.NotebookUserAuthorizationFilter;
import com.al3xkras.web_notebook_api.user_service.model.NotebookUserAuthenticationManager;
import com.al3xkras.web_notebook_api.user_service.model.oAuthAuthenticationSuccessHandler;
import com.al3xkras.web_notebook_api.user_service.service.OAuth2UserService;
import com.al3xkras.web_notebook_api.user_service.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public AuthenticationManager authenticationManager(UserService userService){
        return new NotebookUserAuthenticationManager(userService);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http,
                                         AuthenticationManager authenticationManager,
                                         OAuth2UserService oauthUserService,
                                         UserService userService,
                                         PasswordEncoder passwordEncoder) throws Exception {

        NotebookUserAuthenticationFilter notebookUserAuthenticationFilter = new NotebookUserAuthenticationFilter(authenticationManager,"/login", userService, passwordEncoder);
        NotebookUserAuthorizationFilter notebookUserAuthorizationFilter = new NotebookUserAuthorizationFilter();
        http.csrf().and().cors().disable()
                .authorizeRequests()
                .antMatchers("/oauth/**").permitAll()
                .antMatchers("/**").permitAll();
        http.addFilterBefore(notebookUserAuthenticationFilter,BasicAuthenticationFilter.class);
        http.addFilterBefore(notebookUserAuthorizationFilter,NotebookUserAuthenticationFilter.class);
        http.oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(oauthUserService)
                .and()
                .successHandler(new oAuthAuthenticationSuccessHandler(userService));
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(11);
    }
}
