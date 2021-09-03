package com.example.findusersservice.controllers;

import com.example.findusersservice.models.User;
import com.example.findusersservice.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.example.findusersservice.config.Constants.LONDON_PATH_V1;

@RestController
@Slf4j
public class FindUsersControllerImpl implements FindUsersController {

    UserService userService;

    FindUsersControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    @GetMapping(LONDON_PATH_V1)
    public ResponseEntity<List<User>> findUsers() {
        log.info("Request received on path {}", LONDON_PATH_V1);
        return ResponseEntity.ok(this.userService.getUsers());
    }

}
