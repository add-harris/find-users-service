package com.example.findusersservice.services;

import com.example.findusersservice.models.User;

import java.util.List;

public interface AreaFilterService {

    public List<User> getUsersWithinArea(List<User> users);

}