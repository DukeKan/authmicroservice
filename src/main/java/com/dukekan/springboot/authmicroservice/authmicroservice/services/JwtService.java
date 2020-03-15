package com.dukekan.springboot.authmicroservice.authmicroservice.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.dukekan.springboot.authmicroservice.authmicroservice.configs.AuthConfig;
import com.dukekan.springboot.authmicroservice.authmicroservice.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

    @Autowired
    private AuthConfig authConfig;

    public String createToken(User user) throws JWTCreationException {
        Algorithm algorithm = Algorithm.HMAC256(authConfig.getJwtSecret());
        String token = JWT.create()
                .withIssuer(authConfig.getJwtIssuer())
                .withClaim(authConfig.getJwtUserIdClaim(), user.getUsername())
                .sign(algorithm);
        return token;
    }
}
