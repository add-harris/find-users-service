package com.example.findusersservice.services;

import com.example.findusersservice.config.AppConfig;
import com.example.findusersservice.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private WebClient webClient;

    UserServiceImpl(AppConfig appConfig, WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                            .baseUrl(appConfig.getBaseBackendUrl())
                            .build();
    }

    @Override
    public List<User> getUsers() {
        log.info("getting users");
        return null;
    }

}
