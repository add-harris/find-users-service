package com.example.findusersservice.services;

import com.example.findusersservice.config.AppConfig;
import com.example.findusersservice.models.User;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.example.findusersservice.utils.TestFixtures.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class UserServiceImplTest {

    private UserServiceImpl userService;
    private AppConfig testAppConfig;
    private WebClient testWebClient;
    private AreaFilterService mockAreaFilterService;

    private static final WireMockServer wireMockServer = new WireMockServer();

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
        this.testAppConfig = new AppConfig(testBaseUrl, testCityEndpoint, testUsersEndpoint);
        this.testWebClient = WebClient.builder().baseUrl(testBaseUrl).build();
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

        stubFor(get(testCityEndpoint).willReturn(ok()
                        .withHeader("Content-Type", APPLICATION_JSON)
                        .withBody(basicStubbedUsersJson())
                )
        );
        stubUserEndpoint();

        List<User> users = this.userService.getUsers();

        WireMock.verify(1, getRequestedFor(urlEqualTo(testCityEndpoint)));
        assertEquals(stubUserJeff, users.get(0));
        assertEquals(stubUserBill, users.get(1));
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
        stubFor(get(testUsersEndpoint).willReturn(ok()
                        .withHeader("Content-Type", APPLICATION_JSON)
                        .withBody(centralLondonUsersJson())
                )
        );
        given(mockAreaFilterService.getUsersWithinArea(anyList())).willReturn(centralLondonUsers());

        this.userService.getUsers();

        WireMock.verify(1, getRequestedFor(urlEqualTo(testUsersEndpoint)));
        Mockito.verify(mockAreaFilterService, times(1)).getUsersWithinArea(centralLondonUsers());
    }

    @Test
    void returns_filtered_users_from_area_filter_service () throws Exception {
        stubCityEndpoint();
        stubFor(get(testUsersEndpoint).willReturn(ok()
                        .withHeader("Content-Type", APPLICATION_JSON)
                        .withBody(centralLondonUsersJson())
                )
        );
        given(mockAreaFilterService.getUsersWithinArea(anyList())).willReturn(allLondonUsers());

        List<User> result = this.userService.getUsers();

        assertEquals(allLondonUsers(), result);
    }

    private void stubCityEndpoint() {
        stubFor(get(testCityEndpoint).willReturn(ok()));
    }

    private void stubUserEndpoint() {
        stubFor(get(testUsersEndpoint).willReturn(ok()));
    }

}