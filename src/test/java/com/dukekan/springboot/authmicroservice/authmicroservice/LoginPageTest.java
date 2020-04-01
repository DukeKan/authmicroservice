package com.dukekan.springboot.authmicroservice.authmicroservice;

import com.dukekan.springboot.authmicroservice.authmicroservice.configs.AuthConfig;
import com.dukekan.springboot.authmicroservice.authmicroservice.services.AuthUserDetailsService;
import com.dukekan.springboot.authmicroservice.authmicroservice.support.MockHelpers;
import com.dukekan.springboot.authmicroservice.authmicroservice.support.security.LoginAction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
public class LoginPageTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthConfig authConfig;
    @MockBean
    private AuthUserDetailsService authUserDetailsService;

    @BeforeEach
    public void setUp() {
        when(authConfig.getAuthRedirect()).thenReturn("redirect");
        when(authConfig.getJwtCookieName()).thenReturn("jwt");
        when(authConfig.getJwtSecret()).thenReturn("someRandom");
        when(authConfig.getJwtIssuer()).thenReturn("noMatter");
        when(authConfig.getJwtUserIdClaim()).thenReturn("doesntChecked");
        when(authConfig.getJwtCookieDomain()).thenReturn("localhost");    

        MockHelpers.mockTestUserCredentials(authUserDetailsService);
    }

    @Test
    public void testLoginPageResponse() throws Exception {
        // act
        ResultActions resultActions = mockMvc.perform(get("/login"));

        // asset
        resultActions.andExpect(status().isOk());
    }

    @Test
    public void testCorrectUserLogin() throws Exception {
        // act
        ResultActions loginAction = new LoginAction(mockMvc)
                .setUsername("test")
                .setPassword("test")
                .login();

        // asset
        loginAction.andExpect(status().is2xxSuccessful())
            .andExpect(cookie().exists(authConfig.getJwtCookieName()));
    }

    @Test
    public void testCorrectUserLoginWithRedirect() throws Exception {
        // act
        ResultActions loginAction = new LoginAction(mockMvc)
                .setUsername("test")
                .setPassword("test")
                .setRedirect("https://google.com")
                .login();

        // asset
        loginAction.andExpect(status()
                .is3xxRedirection())
                .andExpect(redirectedUrl(("https://google.com")))
                .andExpect(cookie().exists(authConfig.getJwtCookieName()));
    }

    @Test
    public void testWrongUserLogin() throws Exception {
        // act
        ResultActions loginAction = new LoginAction(mockMvc)
                .setUsername("dfghdifghdfigd")
                .setPassword("9824r08h08345h")
                .login();

        // asset
        loginAction.andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(cookie().doesNotExist(authConfig.getJwtCookieName()));
    }
}
