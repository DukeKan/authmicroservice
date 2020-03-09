package com.dukekan.springboot.authmicroservice.authmicroservice.filters;

import com.dukekan.springboot.authmicroservice.authmicroservice.configs.AuthConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.springframework.util.StringUtils.isEmpty;

@Component
public class RedirectRememberingFilter implements Filter {

    @Autowired
    private AuthConfig authConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String redirect = request.getParameter(authConfig.getAuthRedirect());
        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession(false);
        if (session != null && !isEmpty(redirect)) {
            req.getSession().setAttribute(authConfig.getAuthRedirect(), redirect);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
}
