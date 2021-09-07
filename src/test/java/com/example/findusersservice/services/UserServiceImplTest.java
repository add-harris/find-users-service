package com.example.findusersservice.services;

import com.example.findusersservice.config.AppConfig;
import com.example.findusersservice.models.User;
import com.example.findusersservice.utils.WireMockTest;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import java.util.List;

import static com.example.findusersservice.utils.TestFixtures.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;



class UserServiceImplTest extends WireMockTest {

    private UserServiceImpl userService;
    private AreaFilterService mockAreaFilterService;

    @BeforeEach
    protected void setUp() {
        AppConfig testAppConfig = new AppConfig(testApiBaseUrl, testCityEndpoint, testUsersEndpoint);
        WebClient testWebClient = WebClient.builder().baseUrl(testApiBaseUrl).build();
        this.mockAreaFilterService = mock(AreaFilterService.class);
        this.userService = new UserServiceImpl(testAppConfig, testWebClient, mockAreaFilterService);

        wireMockServer.resetAll();
    }

    @Test
    void make_call_to_test_city_endpoint () {
        stubCityEndpoint();
        stubUserEndpoint();
        this.userService.getUsers();
        WireMock.verify(1, getRequestedFor(urlEqualTo(testCityEndpoint)));
    }

    @Test
    void returns_london_city_users () throws Exception {
        stubCityEndpointWithResponse(basicStubbedUsersJson());
        stubUserEndpoint();

        List<User> users = this.userService.getUsers();

        WireMock.verify(1, getRequestedFor(urlEqualTo(testCityEndpoint)));
        assertEquals(cityEndpointUser1, users.get(0));
        assertEquals(cityEndpointUser2, users.get(1));
    }

    @Test
    void make_call_to_test_users_endpoint () {
        stubCityEndpoint();
        stubUserEndpoint();
        this.userService.getUsers();
        WireMock.verify(1, getRequestedFor(urlEqualTo(testUsersEndpoint)));
    }

    @Test
    void passes_results_from_user_endpoint_to_area_filter_service () throws Exception {
        stubCityEndpoint();
        stubUserEndpointWithResponse(centralLondonUsersJson());
        given(mockAreaFilterService.getUsersWithinArea(anyList())).willReturn(centralLondonUsers());

        this.userService.getUsers();

        WireMock.verify(1, getRequestedFor(urlEqualTo(testUsersEndpoint)));
        Mockito.verify(mockAreaFilterService, times(1)).getUsersWithinArea(centralLondonUsers());
    }

    @Test
    void returns_filtered_users_from_area_filter_service () throws Exception {
        stubCityEndpoint();
        stubUserEndpoint();
        given(mockAreaFilterService.getUsersWithinArea(anyList())).willReturn(allLondonUsers());

        List<User> result = this.userService.getUsers();

        assertEquals(allLondonUsers(), result);
    }

    @Test
    void returns_combined_list_users_from_both_city_endpoint_and_area_filter_service () throws Exception {
        stubCityEndpointWithResponse(basicStubbedUsersJson());
        stubUserEndpoint();

        given(mockAreaFilterService.getUsersWithinArea(anyList())).willReturn(allLondonUsers());

        List<User> result = this.userService.getUsers();

        assertEquals(allExpectedLondonUsers(), result);
    }

}