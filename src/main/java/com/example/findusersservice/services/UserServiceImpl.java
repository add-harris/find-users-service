package com.example.findusersservice.services;

import com.example.findusersservice.config.AppConfig;
import com.example.findusersservice.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final WebClient webClient;
    private final AreaFilterService areaFilterService;

    private final String cityApiEndpoint;
    private final String userApiEndpoint;

    UserServiceImpl(AppConfig appConfig, WebClient webClient, AreaFilterService areaFilterService) {
        this.webClient = webClient;
        this.areaFilterService = areaFilterService;
        this.cityApiEndpoint = appConfig.getCityEndpoint();
        this.userApiEndpoint = appConfig.getUsersEndpoint();
    }

    @Override
    public List<User> getUsers() {
        log.info("getting users");

        log.info("preparing request to city endpoint {} for London users", cityApiEndpoint);
        Flux<User> londonUsers = getLondonCityUsers();

        log.info("preparing request to users endpoint {} for all users", userApiEndpoint);
        Flux<User> allUsers = getLondonAreaUsers();

        log.info("filtering for only users within designated area");
        Flux<User> londonAreaUsers = allUsers.filter(areaFilterService::isWithinArea);

        return Flux.merge(londonUsers, londonAreaUsers)
                .toStream()
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());

    }

    private Flux<User> getLondonCityUsers() {
        return getUsersApiCall(cityApiEndpoint);
    }

    private Flux<User> getLondonAreaUsers() {
        return getUsersApiCall(userApiEndpoint);
    }

    private Flux<User> getUsersApiCall(String endpoint) {
        return this.webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToFlux(User.class)
                .doOnComplete(() -> log.info("request to {} completed successfully", endpoint))
                .onErrorResume(error -> Flux.error(new RuntimeException(error.getMessage(), error)));
    }

}
