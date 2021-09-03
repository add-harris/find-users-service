package com.example.findusersservice.services;

import com.example.findusersservice.config.AppConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    private UserServiceImpl userService;

    @BeforeEach
    void setUp() {
        AppConfig testAppConfig = new AppConfig("localhost");
        this.userService = new UserServiceImpl(testAppConfig, WebClient.builder());
    }

    @Test
    void service_makes_call_to_test_endpoint () {



    }




}