package com.al3xkras.web_notebook_api.user_service.model;

import com.al3xkras.web_notebook_api.user_service.entity.User;
import com.al3xkras.web_notebook_api.user_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Service
public class oAuthAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserService userService;

    public oAuthAuthenticationSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();

        User user = userService.processOAuthPostLogin(oauthUser.getEmail());
        log.info("authenticated using google oAuth: "+oauthUser);
        SecurityContextHolder.getContext().setAuthentication(user);
        response.sendRedirect("/");
    }
}
