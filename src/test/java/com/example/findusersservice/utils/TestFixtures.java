package com.example.findusersservice.utils;

import com.example.findusersservice.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class TestFixtures {

    /**
     * Generic stubbed users to be returned by the city endpoint "/city/London/users"
     */

    public static final User cityEndpointUser1 = new User(
            1,
            "Jeff",
            "GoldBlum",
            "111.11.111.111",
            "jeffgold@hotmail.com",
            -0.007956,
            51.535055
    );

    public static final User cityEndpointUser2 = new User(
            2,
            "Bill",
            "Murray",
            "222.22.222.222",
            "billmurr@gmail.com",
            -0.043999,
            51.529989
    );

    /**
     * Central London Users - people who live close to centre of London
     */

    // Distance 0.82445 miles from centre
    public static final User centralLondonUser1 = stubUserGenerator(3, -0.135364, 51.496436);

    // Distance 0.85065 miles from centre
    public static final User centralLondonUser2 = stubUserGenerator(4, -0.109803, 51.502293);

    // Distance 0.88557 miles from centre
    public static final User centralLondonUser3 = stubUserGenerator(5, -0.116573, 51.518145);

    /**
    * Outer London Users - people who do not live close to the centre of London but still within the 50 mile radius
    */

    // Distance 49.88 miles West of centre
    // coordinates 51.4094913, -1.2758247
    public static final User outerLondonUser1 = stubUserGenerator(6,-1.2758247, 51.4094913);

    // Distance 49.36 miles North of centre
    // coordinates 52.217501, -0.253763
    public static final User outerLondonUser2 = stubUserGenerator(7, -0.253763, 52.217501);

    // Distance 49.02 miles East of centre
    // coordinates 51.309199, 0.964389
    public static final User outerLondonUser3 = stubUserGenerator(8, 0.964389, 51.309199);

    // Distance 49.72 miles South of centre
    // coordinates 50.810476, 0.158110
    public static final User outerLondonUser4 = stubUserGenerator(9, 0.158110, 50.810476);

    /**
     * Outside London Area Users - people who live just outside of the 50 mile search area
     */

    // Distance 50.06 miles West of centre
    // Coordinates 51.409584, -1.279978
    public static final User outsideAreaUser1 = stubUserGenerator(11, -1.279978, 51.409584);

    // Distance 50.03 miles North of centre
    // Coordinates 52.227106, -0.25755
    public static final User outsideAreaUser2 = stubUserGenerator(11, -0.25755, 52.227106);

    // Distance 50.39 miles East of centre
    // Coordinates 51.310280, 0.997813
    public static final User outsideAreaUser3 = stubUserGenerator(11, 0.997813, 51.310280);

    // Distance 50.11 miles South of centre
    // Coordinates 50.804130, 0.154945
    public static final User outsideAreaUser4 = stubUserGenerator(11, 0.154945, 50.804130);

    private static User stubUserGenerator(int id, double longitude, double latitude) {
        return new User(
                id,
                "Stub",
                "User",
                "a-stub-user@gmail.com",
                "999.99.999.999",
                longitude,
                latitude
        );
    }

    public static List<User> cityEndpointUsers() {
        return List.of(cityEndpointUser1, cityEndpointUser2);
    }

    public static List<User> centralLondonUsers() {
        return List.of(centralLondonUser1, centralLondonUser2, centralLondonUser3);
    }

    public static List<User> allLondonUsers() {
        return List.of(centralLondonUser1, centralLondonUser2, centralLondonUser3,
                outerLondonUser1, outerLondonUser2, outerLondonUser3, outerLondonUser4);
    }

    public static List<User> outsideLondonUsers() {
        return List.of(outsideAreaUser1, outsideAreaUser2, outsideAreaUser3, outsideAreaUser4);
    }

    public static List<User> bothInisdeAndOutsideAreaUsers() {
        return List.of(centralLondonUser1, centralLondonUser2, centralLondonUser3,
                outerLondonUser1, outerLondonUser2, outerLondonUser3, outerLondonUser4,
                outsideAreaUser1, outsideAreaUser2, outsideAreaUser3, outsideAreaUser4);
    }

    public static List<User> allExpectedLondonUsers() {
        return List.of(cityEndpointUser1, cityEndpointUser2,
                centralLondonUser1, centralLondonUser2, centralLondonUser3,
                outerLondonUser1, outerLondonUser2, outerLondonUser3, outerLondonUser4);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String basicStubbedUsersJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(cityEndpointUsers());
    }

    public static String centralLondonUsersJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(centralLondonUsers());
    }

}
