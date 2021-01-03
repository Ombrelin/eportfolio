package com.arsene.eportfolio.integration.auth;

import com.arsene.eportfolio.EportfolioApplication;
import com.arsene.eportfolio.model.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EportfolioApplication.class)
@AutoConfigureMockMvc
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@TestPropertySource(properties = {"username=John Shepard", "password=$2y$10$zVpNcmtFnya0GIOb6v3dBeadXX.lVnO2enKc4Uh05bz2Cg63/SLnm"})
public class IntegrationAuth {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mvc;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void loginCorrectCredentials_returnsToken() throws Exception {
        User user = new User();
        user.setUsername("John Shepard");
        user.setPassword("Tali");

        MvcResult mvcResult = mvc.perform(post("/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();
        var token = mvcResult.getResponse().getHeader("Authorization");
        assertNotNull(
                "JWT Token should be present",
                mvcResult.getResponse().getHeader("Authorization")
        );
    }

    @Test
    void loginIncorrectCredentials_unauthorized() throws Exception {
        User user = new User();
        user.setUsername("John Shepard");
        user.setPassword("Talii");

        MvcResult mvcResult = mvc.perform(post("/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().is4xxClientError())
                .andReturn();

        assertEquals(
                "JWT Token should be present",
                401,
                mvcResult.getResponse().getStatus()
        );
    }
}
