package com.example.findusersservice.integration;

import com.example.findusersservice.models.User;
import com.example.findusersservice.utils.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.example.findusersservice.config.Constants.USERS_LONDON_PATH_V1;
import static com.example.findusersservice.utils.TestFixtures.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
@ActiveProfiles("test")
public class FindUsersServiceIT extends WireMockTest {

    @Autowired
    private WebTestClient webTestClient;

    @Override
    protected void setUp() {
        wireMockServer.resetAll();
    }

    // returns city users
    // returns filtered user users
    // returns combined city and users

    // returns PROBLEM_UPSTREAM on backend 500
    // returns PROBLEM_UPSTREAM on backend 404
    // returns UNEXPECTED_RESPONSE on malformed json
    // returns UNEXPECTED_ERROR on unexpected error occurring


    @Test
    void application_returns_200_response_on_GET() throws Exception {
        stubCityEndpoint();
        stubUserEndpoint();

        callApplication()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void application_returns_user_retrieved_from_city_endpoint() throws Exception {
        stubCityEndpointWithResponse(cityEndpointUsersJson());
        stubUserEndpoint();

        callApplication()
                .expectStatus()
                .isOk()
                .expectBodyList(User.class)
                .contains(cityEndpointUser1)
                .contains(cityEndpointUser2);

    }

    private WebTestClient.ResponseSpec callApplication() {
        return this.webTestClient
                .get()
                .uri(USERS_LONDON_PATH_V1)
                .exchange();
    }
}
