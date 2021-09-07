package com.example.findusersservice.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static com.example.findusersservice.utils.TestFixtures.*;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.ok;
import static org.mockito.Mockito.mock;

public abstract class WireMockTest {

    public static final WireMockServer wireMockServer = new WireMockServer();

    @BeforeAll
    private static void beforeAll() {
        wireMockServer.start();
    }

    @AfterAll
    private static void afterAll() {
        wireMockServer.stop();
    }

    @BeforeEach
    protected abstract void setUp();

    public void stubCityEndpoint() {
        stubFor(get(testCityEndpoint).willReturn(ok()));
    }

    public void stubUserEndpoint() {
        stubFor(get(testUsersEndpoint).willReturn(ok()));
    }

    public void stubCityEndpointWithResponse(String body) {
        stubEndpointWithResponse(testCityEndpoint, body);
    }

    public void stubUserEndpointWithResponse(String body) {
        stubEndpointWithResponse(testUsersEndpoint, body);
    }

    public void stubEndpointWithResponse(String endpoint, String body) {
        stubFor(get(endpoint).willReturn(ok()
                        .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                        .withBody(body)));
    }

    public void stubForErrorStatus(String endpoint, int status) {
        stubFor(get(endpoint).willReturn(status(status)));
    }

    public void stubEndpointWithFileResponse(String endpoint, String filePath) {
        stubFor(get(endpoint).willReturn(ok()
                .withHeader(CONTENT_TYPE, APPLICATION_JSON)
                .withBodyFile(filePath)));
    }

}

