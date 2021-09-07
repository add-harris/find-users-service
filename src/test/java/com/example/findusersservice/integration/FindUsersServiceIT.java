package com.example.findusersservice.integration;

import com.example.findusersservice.utils.WireMockTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import static com.example.findusersservice.config.Constants.USERS_LONDON_PATH_V1;

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

    @Test
    void controller_returns_200_response_on_GET() throws Exception {

        stubCityEndpoint();
        stubUserEndpoint();

        this.webTestClient
                .get()
                .uri(USERS_LONDON_PATH_V1)
                .exchange()
                .expectStatus()
                .isOk();

    }

}
