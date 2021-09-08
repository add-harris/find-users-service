package com.example.findusersservice.controllers;

import com.example.findusersservice.models.User;
import com.example.findusersservice.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.findusersservice.config.Constants.*;

@RestController
@Slf4j
@Tag(name = API_TAG, description = API_DESCRIPTION)
public class FindUsersControllerImpl implements FindUsersController {

    private final UserService userService;

    FindUsersControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = USERS_LONDON_PATH_V1, produces = APPLICATION_JSON)
    public ResponseEntity<List<User>> findUsers() {
        log.info("Request received on path {}", USERS_LONDON_PATH_V1);
        return ResponseEntity.ok(this.userService.getUsers());
    }

}
