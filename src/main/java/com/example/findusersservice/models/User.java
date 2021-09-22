package com.example.findusersservice.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.io.Serializable;


@Value
public class User implements Serializable {

    @JsonCreator
    public User(@JsonProperty(value = "id",required = true) int id,
                @JsonProperty("first_name") String firstName,
                @JsonProperty("last_name") String lastName,
                @JsonProperty("ip_address") String ipAddress,
                @JsonProperty("email") String email,
                @JsonProperty(value = "longitude", required = true) double longitude,
                @JsonProperty(value = "latitude", required = true) double latitude) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.ipAddress = ipAddress;
        this.email = email;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    int id;
    String firstName;
    String lastName;
    String ipAddress;
    String email;
    double longitude;
    double latitude;

}
