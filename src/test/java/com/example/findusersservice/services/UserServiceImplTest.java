package com.example.findusersservice.services;

import com.example.findusersservice.config.AppConfig;
import com.example.findusersservice.models.User;
import com.example.findusersservice.utils.WireMockTest;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static com.example.findusersservice.utils.TestFixtures.*;
import static com.github.tomakehurst.wiremock.client.WireMock.getRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.*;
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
        stubCityEndpointWithResponse(cityEndpointUsersJson());
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
    void checks_each_user_from_user_endpoint_is_within_area () throws Exception {
        stubCityEndpoint();
        stubUserEndpointWithResponse(centralLondonUsersJson());
        given(mockAreaFilterService.isWithinArea(any(User.class))).willReturn(true);

        this.userService.getUsers();

        WireMock.verify(1, getRequestedFor(urlEqualTo(testUsersEndpoint)));

        Mockito.verify(mockAreaFilterService, times(1)).isWithinArea(centralLondonUser1);
        Mockito.verify(mockAreaFilterService, times(1)).isWithinArea(centralLondonUser2);
        Mockito.verify(mockAreaFilterService, times(1)).isWithinArea(centralLondonUser3);
    }

    @Test
    void returns_filtered_users_from_area_filter_service () throws Exception {
        stubCityEndpoint();
        stubUserEndpointWithResponse(allLondonUsersJson());
        given(mockAreaFilterService.isWithinArea(any(User.class))).willReturn(true);

        List<User> result = this.userService.getUsers();

        assertEquals(allLondonUsers(), result);
    }

    @Test
    void returns_combined_list_users_from_both_city_endpoint_and_area_filter_service () throws Exception {
        stubCityEndpointWithResponse(cityEndpointUsersJson());
        stubUserEndpointWithResponse(allLondonUsersJson());
        given(mockAreaFilterService.isWithinArea(any(User.class))).willReturn(true);

        List<User> result = this.userService.getUsers();

        assertEquals(allExpectedLondonUsers(), result);
    }

    @Test
    void handles_500_error_returned_from_city_endpoint () throws Exception {
        stubForErrorStatus(testCityEndpoint, 500);
        stubUserEndpoint();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            this.userService.getUsers();
        });

        String expectedMessage = "500 Internal Server Error from GET http://localhost:8080/city/London/users";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void handles_404_error_returned_from_city_endpoint () throws Exception {
        stubForErrorStatus(testCityEndpoint, 404);
        stubUserEndpoint();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            this.userService.getUsers();
        });

        String expectedMessage = "404 Not Found from GET http://localhost:8080/city/London/users";
        assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void handles_malformed_json_returned_from_city_endpoint () throws Exception {
        stubEndpointWithFileResponse(testCityEndpoint, "malformedUser.json");
        stubUserEndpoint();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            this.userService.getUsers();
        });

        String expectedMessageSnippet = "JSON decoding error: Cannot deserialize value of type `double` from String \"0.1272° W\": not a valid `double` value (as String to convert);";
        assertTrue(exception.getMessage().contains(expectedMessageSnippet));
    }

    @Test
    void handles_garbage_json_returned_from_city_endpoint () throws Exception {
        stubCityEndpointWithResponse("{,]}");
        stubUserEndpoint();

        Exception exception = assertThrows(RuntimeException.class, () -> {
            this.userService.getUsers();
        });

        String expectedMessageSnippet = "JSON decoding error: Unexpected character (',' (code 44)): was expecting double-quote to start field name;";
        assertTrue(exception.getMessage().contains(expectedMessageSnippet));
    }

}