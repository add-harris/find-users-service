package com.example.findusersservice.services;

import com.example.findusersservice.config.AppConfig;
import com.example.findusersservice.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.example.findusersservice.utils.TestFixtures.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceImplTest {

    private UserServiceImpl userService;

    private static WireMockServer wireMockServer = new WireMockServer();

    private final String testBaseUrl = "http://localhost:8080";
    private final String testCityEndpoint = "/city/London/users";
    private final String testUsersEndpoint = "/users";
    private final String APPLICATION_JSON = "application/json";

    @BeforeAll
    private static void beforeAll() {
        wireMockServer.start();
    }

    @AfterAll
    private static void afterAll() {
        wireMockServer.stop();
    }

    @BeforeEach
    void setUp() {
        AppConfig testAppConfig = new AppConfig(testBaseUrl, testCityEndpoint, testUsersEndpoint);
        WebClient webClient = WebClient.builder().baseUrl(testBaseUrl).build();
        this.userService = new UserServiceImpl(testAppConfig, webClient);

        wireMockServer.resetAll();
    }

    @Test
    void make_call_to_test_city_endpoint () {
        stubFor(get(testCityEndpoint).willReturn(ok()));
        this.userService.getUsers();
        verify(1, getRequestedFor(urlEqualTo(testCityEndpoint)));
    }

    @Test
    void returns_london_city_users () throws JsonProcessingException {

        stubFor(get(testCityEndpoint).willReturn(ok()
                        .withHeader("Content-Type", APPLICATION_JSON)
                        .withBody(basicStubbedUsersJson())
                )
        );

        List<User> users = this.userService.getUsers();

        verify(1, getRequestedFor(urlEqualTo(testCityEndpoint)));
        assertEquals(stubUserJeff, users.get(0));
        assertEquals(stubUserBill, users.get(1));
    }

    @Test
    void make_call_to_test_users_endpoint () {
        stubFor(get(testUsersEndpoint).willReturn(ok()));
        this.userService.getUsers();
        verify(1, getRequestedFor(urlEqualTo(testUsersEndpoint)));
    }

}