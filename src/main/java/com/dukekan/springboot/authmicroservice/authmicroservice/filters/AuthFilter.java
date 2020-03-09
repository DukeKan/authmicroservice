package com.dukekan.springboot.authmicroservice.authmicroservice.filters;

import com.dukekan.springboot.authmicroservice.authmicroservice.configs.AuthConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Component
public class AuthFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private AuthConfig authConfig;

    public void onSuccessLogin (ServletRequest request, ServletResponse response)
            throws IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        logger.info( "Starting request filter processing for : {}", req.getRequestURI());

        // user is might be authorized earlier in the AbstractAuthenticationProcessingFilter
        Principal userPrincipal = SecurityContextHolder.getContext().getAuthentication();
        String redirect = (String) req.getSession().getAttribute(authConfig.getAuthRedirect());
        if (userPrincipal != null) {
            if (!StringUtils.isEmpty(redirect)) {
                // TODO form JWT, add to cookie and get request
                req.getSession().removeAttribute(authConfig.getAuthRedirect());
                resp.sendRedirect(redirect);
            }
        }

        logger.info("End request filter processing for : {}", req.getRequestURI());
    }
}
