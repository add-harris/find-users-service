package com.example.findusersservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private String firstName;
    private String lastName;
    private String email;
    private String ipAddress;
    private double longitude;
    private double latitude;

}
