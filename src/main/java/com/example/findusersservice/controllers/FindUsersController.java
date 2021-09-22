package com.example.findusersservice.controllers;

import com.example.findusersservice.models.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface FindUsersController {

    public ResponseEntity<List<User>> findUsers();

}
