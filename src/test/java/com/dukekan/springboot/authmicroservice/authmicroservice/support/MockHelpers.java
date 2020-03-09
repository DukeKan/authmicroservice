package com.dukekan.springboot.authmicroservice.authmicroservice.support;

import com.dukekan.springboot.authmicroservice.authmicroservice.entities.User;
import com.dukekan.springboot.authmicroservice.authmicroservice.secureweb.UserPrincipal;
import com.dukekan.springboot.authmicroservice.authmicroservice.services.AuthUserDetailsService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.ArrayList;
import java.util.Collection;

import static org.mockito.Mockito.when;

public class MockHelpers {

    public static void mockTestUserCredentials(AuthUserDetailsService authUserDetailsService) {
        User testUser = new User();
        testUser.setUsername("test");
        testUser.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("test"));
        when(authUserDetailsService.loadUserByUsername("test")).thenReturn(new UserPrincipal(testUser) {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                SimpleGrantedAuthority admin = new SimpleGrantedAuthority("ADMIN");
                ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(admin);
                return authorities;
            }
        });
    }
}
