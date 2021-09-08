package com.example.findusersservice.integration;

import com.example.findusersservice.error.ErrorResponse;
import org.junit.jupiter.api.Test;

import static com.example.findusersservice.config.Constants.*;
import static com.example.findusersservice.utils.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindUsersServiceExceptionsIT extends IntegrationTest {

    @Test
    void return_PROBLEM_UPSTREAM_message_when_500_received_from_backend() throws Exception {
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
    void return_PROBLEM_UPSTREAM_message_if_404_received_from_backend() throws Exception {
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

    @Test
    void return_UNEXPECTED_RESPONSE_message_if_unexpected_JSON_received_from_backend() throws Exception {
        stubEndpointWithFileResponse(testCityEndpoint, MALFORMED_USERS_FILE_PATH);
        stubUserEndpoint();

        ErrorResponse error = callApplication()
                .expectStatus()
                .isEqualTo(500)
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        assertEquals(500, error.getStatus());
        assertEquals(UNEXPECTED_RESPONSE, error.getMessage());
    }

    @Test
    void return_UNEXPECTED_RESPONSE_message_if_garbage_JSON_received_from_backend() throws Exception {
        stubCityEndpoint();
        stubUserEndpointWithResponse("{,]}");

        ErrorResponse error = callApplication()
                .expectStatus()
                .isEqualTo(500)
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        assertEquals(500, error.getStatus());
        assertEquals(UNEXPECTED_RESPONSE, error.getMessage());
    }

    @Test
    void return_UNEXPECTED_ERROR_message_if_an_unexpected_error_occurs() throws Exception {
        stubForErrorStatus(testCityEndpoint, 401);
        stubUserEndpoint();

        ErrorResponse error = callApplication()
                .expectStatus()
                .isEqualTo(500)
                .expectBody(ErrorResponse.class)
                .returnResult().getResponseBody();

        assertEquals(500, error.getStatus());
        assertEquals(UNEXPECTED_ERROR, error.getMessage());
    }

}
