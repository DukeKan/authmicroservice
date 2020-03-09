package com.dukekan.springboot.authmicroservice.authmicroservice.support.security;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

import javax.servlet.ServletContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public final class CustomFormLoginRequestBuilder implements RequestBuilder {
    private String username;
    private String password;
    private String redirect;

    private RequestPostProcessor postProcessor = csrf();

    @Override
    public MockHttpServletRequest buildRequest(ServletContext servletContext) {

        MockHttpServletRequest request = post("/login")
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", this.username)
                .param("password", this.password)
                .param("redirect", redirect)
                .buildRequest(servletContext);
        return this.postProcessor.postProcessRequest(request);
    }

    public CustomFormLoginRequestBuilder password(String password) {
        this.password = password;
        return this;
    }

    public CustomFormLoginRequestBuilder redirect(String redirect) {
        this.redirect = redirect;
        return this;
    }

    public CustomFormLoginRequestBuilder user(String username) {
        this.username = username;
        return this;
    }

    public static CustomFormLoginRequestBuilder formLogin() {
        return new CustomFormLoginRequestBuilder();
    }
}