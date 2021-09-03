package com.example.findusersservice.services;

import com.example.findusersservice.config.AppConfig;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private UserServiceImpl userService;

    private static WireMockServer wireMockServer = new WireMockServer();

    private final String testBaseUrl = "http://localhost:8080";
    private final String testCityEndpoint = "/city/London/users";

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
        AppConfig testAppConfig = new AppConfig(testBaseUrl);
        this.userService = new UserServiceImpl(testAppConfig, WebClient.builder());

        wireMockServer.resetAll();
    }

    @Test
    void make_call_to_test_city_endpoint () {

        stubFor(get(testCityEndpoint).willReturn(ok()));

        this.userService.getUsers();

        verify(1, getRequestedFor(urlEqualTo(testCityEndpoint)));

    }

}