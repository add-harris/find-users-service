package com.example.findusersservice.utils;

import com.example.findusersservice.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestFixtures {

    /**
     * Test Constants
     */

    public static final String testApiBaseUrl = "http://localhost:8080";
    public static final String testCityEndpoint = "/city/London/users";
    public static final String testUsersEndpoint = "/users";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";
    public static final String MALFORMED_USERS_FILE_PATH = "malformedUser.json";


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
    // coordinates 51.496436, -0.135364
    public static final User centralLondonUser1 = stubUserGenerator(3, -0.135364, 51.496436);

    // Distance 0.85065 miles from centre
    // coordinates 51.502293, -0.109803
    public static final User centralLondonUser2 = stubUserGenerator(4, -0.109803, 51.502293);

    // Distance 0.88557 miles from centre
    // coordinates 51.518145, -0.116573
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
    public static final User outsideAreaUser1 = stubUserGenerator(10, -1.279978, 51.409584);

    // Distance 50.03 miles North of centre
    // Coordinates 52.227106, -0.25755
    public static final User outsideAreaUser2 = stubUserGenerator(11, -0.25755, 52.227106);

    // Distance 50.39 miles East of centre
    // Coordinates 51.310280, 0.997813
    public static final User outsideAreaUser3 = stubUserGenerator(12, 0.997813, 51.310280);

    // Distance 50.11 miles South of centre
    // Coordinates 50.804130, 0.154945
    public static final User outsideAreaUser4 = stubUserGenerator(13, 0.154945, 50.804130);

    /**
     * Very Far Outside London Area Users - people in the UK but live very far from London
     */

    // Distance 383.35 miles from Centre
    // Coordinates 55.010775, -7.330153
    public static final User northernIrelandUser = stubUserGenerator(14, -7.330153, 55.010775);

    // Distance 516.97 miles from Centre
    // Coordinates 58.493643, -4.819670
    public static final User scotlandUser = stubUserGenerator(14, -4.819670, 58.493643);

    // Distance 261.78 miles from Centre
    // Coordinates 50.046830, -5.658375
    public static final User southWestUser = stubUserGenerator(15, -5.658375, 50.046830);

    /**
     * Edge Case Users - people extremely far from London Centre
     */

    // Distance 1170.75 miles from Centre
    // Coordinates 64.127402, -21.830721
    public static final User icelandUser = stubUserGenerator(16, -21.830721, 64.127402);

    // Distance 4982.05 miles from Centre
    // Coordinates 19.979095, 85.967849
    public static final User indiaUser = stubUserGenerator(17, 85.967849, 19.979095);

    // Distance 11796.23 miles from Centre
    // Coordinates -43.590232, 172.688372
    public static final User newZealandUser = stubUserGenerator(18, 172.688372, -43.590232);


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

    public static List<User> farOutsideLondonUsers() {
        return List.of(northernIrelandUser, scotlandUser, southWestUser);
    }

    public static List<User> internationalUsers() {
        return List.of(icelandUser, indiaUser, newZealandUser);
    }

    public static List<User> allOutsideOfAreaUsers() {
        return List.of(outsideAreaUser1, outsideAreaUser2, outsideAreaUser3, outsideAreaUser4,
                northernIrelandUser, scotlandUser, southWestUser,
                icelandUser, indiaUser, newZealandUser);
    }

    public static List<User> allInsideAndOutsideAreaUsers() {
        return Stream.concat(allLondonUsers().stream(), allOutsideOfAreaUsers().stream()).collect(Collectors.toList());
    }

    public static List<User> allExpectedLondonUsers() {
        return List.of(cityEndpointUser1, cityEndpointUser2,
                centralLondonUser1, centralLondonUser2, centralLondonUser3,
                outerLondonUser1, outerLondonUser2, outerLondonUser3, outerLondonUser4);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String cityEndpointUsersJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(cityEndpointUsers());
    }

    public static String centralLondonUsersJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(centralLondonUsers());
    }

    public static String allLondonUsersJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(allLondonUsers());
    }

    public static String allInsideAndOutsideAreaUsersJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(allInsideAndOutsideAreaUsers());
    }

}
