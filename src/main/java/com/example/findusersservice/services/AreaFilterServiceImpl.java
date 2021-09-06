package com.example.findusersservice.services;

import com.example.findusersservice.models.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@NoArgsConstructor
public class AreaFilterServiceImpl implements AreaFilterService {

    @Override
    public List<User> getUsersWithinRadius(List<User> users) {
        return null;
    }

}
