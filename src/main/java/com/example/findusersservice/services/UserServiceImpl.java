package com.example.findusersservice.services;

import com.example.findusersservice.config.AppConfig;
import com.example.findusersservice.models.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collection;
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
        var londonUsers = getLondonCityUsers();

        log.info("preparing request to users endpoint {} for all users", userApiEndpoint);
        var allUsers = getLondonAreaUsers().map(list ->
            list.stream().filter(areaFilterService::isWithinArea).collect(Collectors.toList())
        );

        return Flux.merge(londonUsers, allUsers)
                .toStream()
                .flatMap(Collection::stream)
                .sorted(Comparator.comparing(User::getId))
                .collect(Collectors.toList());
    }

    private Mono<List<User>> getLondonCityUsers() {
        return getUsersApiCall(cityApiEndpoint);
    }

    private Mono<List<User>> getLondonAreaUsers() {
        return getUsersApiCall(userApiEndpoint);
    }

    private Mono<List<User>> getUsersApiCall(String endpoint) {
        return this.webClient.get()
                .uri(endpoint)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<User>>() {})
                .doOnSuccess(response -> log.info("response successfully received from {}", endpoint))
                .onErrorResume(error -> Mono.error(new RuntimeException(error.getMessage(), error)));
    }

}
