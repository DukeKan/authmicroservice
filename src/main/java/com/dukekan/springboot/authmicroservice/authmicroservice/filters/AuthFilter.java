package com.dukekan.springboot.authmicroservice.authmicroservice.filters;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.dukekan.springboot.authmicroservice.authmicroservice.configs.AuthConfig;
import com.dukekan.springboot.authmicroservice.authmicroservice.entities.User;
import com.dukekan.springboot.authmicroservice.authmicroservice.secureweb.UserPrincipal;
import com.dukekan.springboot.authmicroservice.authmicroservice.services.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@Component
public class AuthFilter {

    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private AuthConfig authConfig;
    @Autowired
    private JwtService jwtService;

    public void onSuccessLogin (ServletRequest request, ServletResponse response)
            throws IOException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        logger.info( "Starting request filter processing for : {}", req.getRequestURI());

        // user is might be authorized earlier in the AbstractAuthenticationProcessingFilter
        Principal userPrincipal = SecurityContextHolder.getContext().getAuthentication();
        String redirect = (String) req.getSession().getAttribute(authConfig.getAuthRedirect());
        if (userPrincipal != null) {
            User user = ((UserPrincipal) ((UsernamePasswordAuthenticationToken) userPrincipal)
                    .getPrincipal()).getUser();
            try {
                String token = jwtService.createToken(user);
                addJwtCookie(resp, token);
            } catch (JWTCreationException e) {
                logger.warn("Can not create JWT", e);
                resp.setStatus(401);
                return;
            }
            if (!StringUtils.isEmpty(redirect)) {
                req.getSession().removeAttribute(authConfig.getAuthRedirect());
                resp.sendRedirect(redirect);
            }
        }

        logger.info("End request filter processing for : {}", req.getRequestURI());
    }

    private void addJwtCookie(HttpServletResponse resp, String token) {
        Cookie cookie = new Cookie(authConfig.getJwtCookieName(), token);
        cookie.setPath("/");
        cookie.setMaxAge(60);
        resp.addCookie(cookie);
    }
}
