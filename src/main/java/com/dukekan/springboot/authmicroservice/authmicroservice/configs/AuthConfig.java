package com.dukekan.springboot.authmicroservice.authmicroservice.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:auth.properties")
public class AuthConfig {

    @Autowired
    private Environment environment;

    public String getAuthRedirect() {
        return environment.getProperty("redirect.fromServiceQueryParam");
    }

}