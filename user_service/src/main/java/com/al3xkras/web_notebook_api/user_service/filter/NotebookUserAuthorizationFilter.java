package com.al3xkras.web_notebook_api.user_service.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class NotebookUserAuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        HttpMethod method = HttpMethod.valueOf(request.getMethod());
        String requestString = "("+method+") "+uri;

        if (NotebookUserAuthenticationFilter.allowedURIs.contains(uri)){
            filterChain.doFilter(request,response);
            log.warn("Security filter chain skipped for request: "+requestString);
            return;
        }
        filterChain.doFilter(request,response);
        //TODO implement
    }
}
