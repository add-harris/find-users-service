package com.example.findusersservice.services;

import com.example.findusersservice.models.User;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Slf4j
@NoArgsConstructor
public class AreaFilterServiceImpl implements AreaFilterService {

    public static final double searchAreaMiles = 50;
    public static final double centreOfLondonLatitude = 51.5074;
    public static final double centreOfLondonLongitude = -0.1278;
    public static final double earthRadius = 3958.8;

    @Override
    public List<User> getUsersWithinArea(List<User> users) {
        return users.stream()
                .filter(this::isWithinArea)
                .collect(Collectors.toList());
    }

    @Override
    public boolean isWithinArea(User user) {
        log.info("checking user with id: {}", user.getId());

        double distance = calculateDistanceFromCenter(user.getLatitude(), user.getLongitude());
        log.info("distance from centre of london calculated at: {}", distance);

        return distance <= searchAreaMiles;
    }

    double calculateDistanceFromCenter(double userLatitude, double userLongitude) {

        // distance between latitudes and longitudes
        double latDiff = Math.toRadians(centreOfLondonLatitude - userLatitude);
        double lonDiff = Math.toRadians(centreOfLondonLongitude - userLongitude);

        // convert to radians
        double userLatitudeRadians = Math.toRadians(userLatitude);
        double centreOfLondonLatitudeRadians = Math.toRadians(centreOfLondonLatitude);

        // apply formulae
        double a = Math.pow(Math.sin(latDiff / 2), 2) +
                Math.pow(Math.sin(lonDiff / 2), 2) *
                        Math.cos(userLatitudeRadians) *
                        Math.cos(centreOfLondonLatitudeRadians);

        double c = 2 * Math.asin(Math.sqrt(a));

        return earthRadius * c;
    }

}
