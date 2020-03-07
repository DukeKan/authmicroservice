package com.dukekan.springboot.authmicroservice.authmicroservice.services;

import com.dukekan.springboot.authmicroservice.authmicroservice.entities.User;
import com.dukekan.springboot.authmicroservice.authmicroservice.repositories.UserRepository;
import com.dukekan.springboot.authmicroservice.authmicroservice.secureweb.UserPrincipal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class AuthUserDetailsService  implements UserDetailsService {

    private Logger logger = LoggerFactory.getLogger(AuthUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void createTestUsers() {
        PasswordEncoder passEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        logger.warn("Creating test user");
        User user = new User();
        user.setUsername("test");
        user.setPassword(passEncoder.encode("test"));
        userRepository.save(user);
        logger.warn("Test user is created");
    }

    @PreDestroy
    public void removeTestUsers() {
        userRepository.deleteAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new UserPrincipal(user);
    }
}
