package com.example.findusersservice.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FindUsersController.class)
class FindUsersControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    protected UserService mockUserService;

    @Test
    void controller_returns_200_response_on_GET() throws Exception {

        mockMvc.perform(get("/v1/london")).andExpect(status().isOk());

    }

    @Test
    void controller_calls_user_service() throws Exception {

        mockMvc.perform(get("/v1/london")).andExpect(status().isOk());

        verify(mockUserService, times(1)).getUsers();

    }

}