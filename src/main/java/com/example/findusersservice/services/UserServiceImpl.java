package com.example.findusersservice.services;

import com.example.findusersservice.config.AppConfig;
import com.example.findusersservice.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private WebClient webClient;
    private AppConfig appConfig;

    UserServiceImpl(AppConfig appConfig, WebClient webClient) {
        this.appConfig = appConfig;
        this.webClient = webClient;
    }

    @Override
    public List<User> getUsers() {
        log.info("getting users");

        log.info("making request to city endpoint to get London city users");
        getLondonCityUsers();

        return null;
    }

    private Mono<List<User>> getLondonCityUsers() {
        return this.webClient.get()
                .uri(appConfig.getCityEndpoint())
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<User>>() {});
    }

}
