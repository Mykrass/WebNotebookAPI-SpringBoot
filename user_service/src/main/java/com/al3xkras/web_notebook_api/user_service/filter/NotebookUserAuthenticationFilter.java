package com.al3xkras.web_notebook_api.user_service.filter;


import com.al3xkras.web_notebook_api.user_service.entity.User;
import com.al3xkras.web_notebook_api.user_service.model.UserDetailsProvider;
import com.al3xkras.web_notebook_api.user_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Optional;

@Slf4j
public class NotebookUserAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    protected static final HashSet<String> allowedURIs = new HashSet<>(Arrays.asList(
            "/login","/login/oauth2/google","/favicon.ico","/error"
    ));

    public NotebookUserAuthenticationFilter(AuthenticationManager authenticationManager, String defaultFilterProcessesUrl, UserService userService, PasswordEncoder passwordEncoder) {
        super(defaultFilterProcessesUrl);
        this.authenticationManager=authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public NotebookUserAuthenticationFilter(AuthenticationManager authenticationManager, UserService userService, PasswordEncoder passwordEncoder) {
        super("/");
        this.authenticationManager=authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)res;
        String uri = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        String requestString = "("+method+") "+uri;

        log.info(uri);
        if (allowedURIs.contains(uri)){
            filterChain.doFilter(request,response);
            log.warn("Security filter chain skipped for request: "+requestString);
            return;
        }

        String prov = request.getParameter("provider");
        UserDetailsProvider provider = prov==null?UserDetailsProvider.LOCAL:UserDetailsProvider.valueOf(prov);

        //TODO test
        //TODO remove hardcoded strings
        User user;
        if (provider.equals(UserDetailsProvider.GOOGLE)){
            user = (User) SecurityContextHolder.getContext().getAuthentication();
            if (!user.isAuthenticated()){
                //TODO remove hardcoded strings
                log.warn("google user is unauthenticated: "+user);
                unsuccessfulAuthentication(request, response, new AuthenticationException("google user is not authenticated"){});
                return;
            }

        } else if (provider.equals(UserDetailsProvider.LOCAL)){
            try {
                user = (User) attemptAuthentication(request,response);
            } catch (AuthenticationException e){
                unsuccessfulAuthentication(request,response,e);
                return;
            }
        } else {
            unsuccessfulAuthentication(request, response, new AuthenticationException("internal server error"){});
            return;
        }
        successfulAuthentication(request,response,filterChain,user);
        filterChain.doFilter(request,response);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String username = request.getParameter("username");
        String passwd = request.getParameter("password");
        if (passwd==null){
            throw new AuthenticationException("invalid credentials"){};
        }
        String password = passwordEncoder.encode(passwd);

        Optional<User> optionalUser = userService.findByUsername(username);
        if (!optionalUser.isPresent()){
            throw new AuthenticationException("user not found"){};
        }
        User user = optionalUser.get();

        if (user.getPassword()==null || user.getPassword().isEmpty() || !user.getPassword().equals(password)){
            throw new AuthenticationException("invalid credentials"){};
        }

        return user;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        log.info("successful authentication");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{" +
                "\"message\":\""+e.getMessage()+"\"" +
                "}");
        response.sendError(HttpStatus.FORBIDDEN.value());
    }
}
