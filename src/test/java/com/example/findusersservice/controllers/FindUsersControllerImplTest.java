package com.example.findusersservice.controllers;

import com.example.findusersservice.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.example.findusersservice.config.Constants.USERS_LONDON_PATH_V1;
import static com.example.findusersservice.utils.TestFixtures.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = FindUsersController.class)
class FindUsersControllerImplTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    protected UserService mockUserService;

    @BeforeEach
    void setUp() {
        given(mockUserService.getUsers()).willReturn(List.of(cityEndpointUser1, cityEndpointUser2));
    }

    @Test
    void controller_returns_200_response_on_GET() throws Exception {

        mockMvc.perform(get(USERS_LONDON_PATH_V1)).andExpect(status().isOk());

    }

    @Test
    void controller_returns_content_type_application_json() throws Exception {

        mockMvc.perform(get(USERS_LONDON_PATH_V1))
                .andExpect(status().isOk())
                .andExpect(header().stringValues(CONTENT_TYPE, APPLICATION_JSON));

    }

    @Test
    void controller_calls_user_service() throws Exception {

        mockMvc.perform(get(USERS_LONDON_PATH_V1)).andExpect(status().isOk());

        verify(mockUserService, times(1)).getUsers();

    }

    @Test
    void controller_returns_users_found_by_user_service() throws Exception {

        mockMvc.perform(get(USERS_LONDON_PATH_V1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(cityEndpointUser1.getId())))
                .andExpect(jsonPath("$[0].first_name", is(cityEndpointUser1.getFirstName())))
                .andExpect(jsonPath("$[0].last_name", is(cityEndpointUser1.getLastName())))
                .andExpect(jsonPath("$[0].email", is(cityEndpointUser1.getEmail())))
                .andExpect(jsonPath("$[0].ip_address", is(cityEndpointUser1.getIpAddress())))
                .andExpect(jsonPath("$[0].longitude", is(cityEndpointUser1.getLongitude())))
                .andExpect(jsonPath("$[0].latitude", is(cityEndpointUser1.getLatitude())))
                .andExpect(jsonPath("$[1].id", is(cityEndpointUser2.getId())))
                .andExpect(jsonPath("$[1].first_name", is(cityEndpointUser2.getFirstName())))
                .andExpect(jsonPath("$[1].last_name", is(cityEndpointUser2.getLastName())))
                .andExpect(jsonPath("$[1].email", is(cityEndpointUser2.getEmail())))
                .andExpect(jsonPath("$[1].ip_address", is(cityEndpointUser2.getIpAddress())))
                .andExpect(jsonPath("$[1].longitude", is(cityEndpointUser2.getLongitude())))
                .andExpect(jsonPath("$[1].latitude", is(cityEndpointUser2.getLatitude())));

    }

}