package com.example.findusersservice.services;

import com.example.findusersservice.config.AppConfig;
import com.example.findusersservice.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

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

        log.info("making request to city endpoint for London users");
        var londonUsers = getLondonCityUsers().collectList().block();

        var londonAreaUsers = getLondonAreaUsers().collectList().block();

        return londonUsers;
    }

    private Flux<User> getLondonCityUsers() {
        return getUsersApiCall(appConfig.getCityEndpoint());
    }

    private Flux<User> getLondonAreaUsers() {
        return getUsersApiCall(appConfig.getUsersEndpoint());
    }

    private Flux<User> getUsersApiCall(String endpoint) {
        return this.webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToFlux(User.class);
    }

}
