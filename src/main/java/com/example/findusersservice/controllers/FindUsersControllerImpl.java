package com.example.findusersservice.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class FindUsersControllerImpl implements FindUsersController {

    @Override
    @GetMapping("/v1/london")
    public ResponseEntity findUsers() {
        log.info("Request received");
        return ResponseEntity.ok().build();
    }

}
