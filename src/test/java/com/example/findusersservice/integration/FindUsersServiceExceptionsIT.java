package com.example.findusersservice.integration;

import com.example.findusersservice.error.ErrorResponse;
import org.junit.jupiter.api.Test;

import static com.example.findusersservice.config.Constants.PROBLEM_UPSTREAM;
import static com.example.findusersservice.config.Constants.UNEXPECTED_RESPONSE;
import static com.example.findusersservice.utils.TestFixtures.testCityEndpoint;
import static com.example.findusersservice.utils.TestFixtures.testUsersEndpoint;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindUsersServiceExceptionsIT extends IntegrationTest {

    // returns PROBLEM_UPSTREAM on backend 500
    // returns PROBLEM_UPSTREAM on backend 404
    // returns UNEXPECTED_RESPONSE on malformed json
    // returns UNEXPECTED_ERROR on unexpected error occurring

    @Test
    void application_returns_PROBLEM_UPSTREAM_message_when_500_received_from_backend() throws Exception {
        stubForErrorStatus(testCityEndpoint, 500);
        stubUserEndpoint();

        ErrorResponse error = callApplication()
                .expectStatus()
                .isEqualTo(500)
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        assertEquals(500, error.getStatus());
        assertEquals(PROBLEM_UPSTREAM, error.getMessage());
    }

    @Test
    void application_returns_PROBLEM_UPSTREAM_message_if_404_received_from_backend() throws Exception {
        stubCityEndpoint();
        stubForErrorStatus(testUsersEndpoint, 404);

        ErrorResponse error = callApplication()
                .expectStatus()
                .isEqualTo(500)
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        assertEquals(500, error.getStatus());
        assertEquals(PROBLEM_UPSTREAM, error.getMessage());
    }

}
