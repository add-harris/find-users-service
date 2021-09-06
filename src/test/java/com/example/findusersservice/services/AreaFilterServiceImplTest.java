package com.example.findusersservice.services;

import com.example.findusersservice.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.example.findusersservice.utils.TestFixtures.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AreaFilterServiceImplTest {

    private AreaFilterServiceImpl areaFilterService;

    @BeforeEach
    void setup() {
        this.areaFilterService = new AreaFilterServiceImpl();
    }

    @Test
    void given_a_user_location_calculate_the_distance_from_centre_of_london() {
        double calculatedDistance = calculateDistance(centralLondonUser1);
        assertEquals(0.82, roundedAnswer(calculatedDistance));
    }

    @Test
    void calculate_distances_correctly_for_central_london_users() {
        double centralLondonUser2Distance = calculateDistance(centralLondonUser2);
        double centralLondonUser3Distance = calculateDistance(centralLondonUser3);
        assertEquals(0.85, roundedAnswer(centralLondonUser2Distance));
        assertEquals(0.89, roundedAnswer(centralLondonUser3Distance));
    }

    @Test
    void calculate_distances_correctly_for_outer_london_users() {
        double outerLondonUser1Distance = calculateDistance(outerLondonUser1);
        double outerLondonUser2Distance = calculateDistance(outerLondonUser2);
        double outerLondonUser3Distance = calculateDistance(outerLondonUser3);
        double outerLondonUser4Distance = calculateDistance(outerLondonUser4);
        assertEquals(49.88, roundedAnswer(outerLondonUser1Distance));
        assertEquals(49.36, roundedAnswer(outerLondonUser2Distance));
        assertEquals(49.02, roundedAnswer(outerLondonUser3Distance));
        assertEquals(49.72, roundedAnswer(outerLondonUser4Distance));
    }

    @Test
    void calculate_distances_correctly_for_outside_london_area_users() {
        double outsideAreaUser1Distance = calculateDistance(outsideAreaUser1);
        double outsideAreaUser2Distance = calculateDistance(outsideAreaUser2);
        double outsideAreaUser3Distance = calculateDistance(outsideAreaUser3);
        double outsideAreaUser4Distance = calculateDistance(outsideAreaUser4);
        assertEquals(50.06, roundedAnswer(outsideAreaUser1Distance));
        assertEquals(50.03, roundedAnswer(outsideAreaUser2Distance));
        assertEquals(50.39, roundedAnswer(outsideAreaUser3Distance));
        assertEquals(50.11, roundedAnswer(outsideAreaUser4Distance));
    }

    @Test
    void given_users_that_are_within_50_miles_return_them() {
        assertEquals(allLondonUsers(), this.areaFilterService.getUsersWithinArea(allLondonUsers()));
    }

    @Test
    void given_users_that_outside_50_miles_exclude_them() {
        assertEquals(List.of(), this.areaFilterService.getUsersWithinArea(outsideLondonUsers()));
    }

    @Test
    void given_both_inside_and_outside_area_users_return_only_inside_users() {
        assertEquals(allLondonUsers(), this.areaFilterService.getUsersWithinArea(allStubbedUsers()));
    }

    private double calculateDistance(User user) {
        return this.areaFilterService.calculateDistanceFromCenter(
                user.getLatitude(),
                user.getLongitude()
        );
    }

    private double roundedAnswer(double numberToRound) {
        return (double)Math.round(numberToRound * 100d) / 100d;
    }

}