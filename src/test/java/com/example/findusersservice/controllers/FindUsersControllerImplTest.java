package com.example.findusersservice.controllers;

import com.example.findusersservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static com.example.findusersservice.config.Constants.LONDON_PATH_V1;

@WebMvcTest(controllers = FindUsersController.class)
class FindUsersControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    protected UserService mockUserService;

    @BeforeEach
    void setUp() {
        given(mockUserService.getUsers()).willReturn(List.of());
    }

    @Test
    void controller_returns_200_response_on_GET() throws Exception {

        mockMvc.perform(get(LONDON_PATH_V1)).andExpect(status().isOk());

    }

    @Test
    void controller_calls_user_service() throws Exception {

        mockMvc.perform(get(LONDON_PATH_V1)).andExpect(status().isOk());

        verify(mockUserService, times(1)).getUsers();

    }

}