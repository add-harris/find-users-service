package com.example.findusersservice.controllers;

import com.example.findusersservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.findusersservice.config.Constants.LONDON_PATH_V1;
import static com.example.findusersservice.utils.TestFixtures.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = FindUsersController.class)
class FindUsersControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    protected UserService mockUserService;

    @BeforeEach
    void setUp() {
        given(mockUserService.getUsers()).willReturn(List.of(stubUserJeff, stubUserBill));
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

    @Test
    void controller_returns_users_found_by_user_service() throws Exception {

        mockMvc.perform(get(LONDON_PATH_V1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is(stubUserJeff.getFirstName())))
                .andExpect(jsonPath("$[0].lastName", is(stubUserJeff.getLastName())))
                .andExpect(jsonPath("$[0].email", is(stubUserJeff.getEmail())))
                .andExpect(jsonPath("$[0].ipAddress", is(stubUserJeff.getIpAddress())))
                .andExpect(jsonPath("$[0].longitude", is(stubUserJeff.getLongitude())))
                .andExpect(jsonPath("$[0].latitude", is(stubUserJeff.getLatitude())))
                .andExpect(jsonPath("$[1].firstName", is(stubUserBill.getFirstName())))
                .andExpect(jsonPath("$[1].lastName", is(stubUserBill.getLastName())))
                .andExpect(jsonPath("$[1].email", is(stubUserBill.getEmail())))
                .andExpect(jsonPath("$[1].ipAddress", is(stubUserBill.getIpAddress())))
                .andExpect(jsonPath("$[1].longitude", is(stubUserBill.getLongitude())))
                .andExpect(jsonPath("$[1].latitude", is(stubUserBill.getLatitude())));

    }

}