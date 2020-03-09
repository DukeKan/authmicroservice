package com.dukekan.springboot.authmicroservice.authmicroservice.support.security;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpSession;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginAction {
    private MockMvc mockMvc;

    private String username;
    private String password;
    private String redirect;

    public LoginAction(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    public ResultActions login() throws Exception {
        // initiate session by get request
        MockHttpServletRequest request = mockMvc.perform(get("/"))
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getRequest();

        HttpSession session = request.getSession();
        CsrfToken csrf = (CsrfToken) request.getAttribute("_csrf");

        assert session != null;
        session.setAttribute("org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN", csrf);

        return mockMvc.perform(post("/login")
                .session((MockHttpSession) session)
                .param("_csrf", csrf.getToken())
                .accept(MediaType.APPLICATION_FORM_URLENCODED)
                .param("username", username)
                .param("password", password)
                .param("redirect", redirect))
                .andDo(print());
    }

    public LoginAction setUsername(String username) {
        this.username = username;
        return this;
    }

    public LoginAction setPassword(String password) {
        this.password = password;
        return this;
    }

    public LoginAction setRedirect(String redirect) {
        this.redirect = redirect;
        return this;
    }
}
