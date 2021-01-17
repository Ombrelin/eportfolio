package com.arsene.eportfolio.integration;

import com.arsene.eportfolio.model.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class IntegrationUtil {

    public static String login(MockMvc mvc, ObjectMapper objectMapper) throws Exception{
        User user = new User();
        user.setUsername("John Shepard");
        user.setPassword("Tali");

        MvcResult mvcResult = mvc.perform(post("/login")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andReturn();
        var token = mvcResult.getResponse().getHeader("Authorization");

        return token;
    }

}
