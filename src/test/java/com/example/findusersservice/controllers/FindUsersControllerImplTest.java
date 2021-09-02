package com.example.findusersservice.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FindUsersController.class)
class FindUsersControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void controller_returns_200_response_on_GET() throws Exception {

        mockMvc.perform(get("/v1/london")).andExpect(status().isOk());

    }

}