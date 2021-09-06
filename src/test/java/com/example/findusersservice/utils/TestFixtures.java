package com.example.findusersservice.utils;

import com.example.findusersservice.models.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class TestFixtures {

    // Generic stubbed users to be returned by the city endpoint "/city/London/usres"

    public static final User stubUserJeff = new User(
            1,
            "Jeff",
            "GoldBlum",
            "111.11.111.111",
            "jeffgold@hotmail.com",
            -0.007956,
            51.535055
    );

    public static final User stubUserBill = new User(
            2,
            "Bill",
            "Murray",
            "222.22.222.222",
            "billmurr@gmail.com",
            -0.043999,
            51.529989
    );

    // Central London Users

    // diff is 1.32 KM
    public static final User centralLondonUser1 = new User(
            3,
            "Howard",
            "Moon",
            "howard82@aol.com",
            "333.33.333.333",
            -0.135364,
            51.496436
    );

    // diff is 1.36 KM
    public static final User centralLondonUser2 = new User(
            4,
            "Vince",
            "Noir",
            "vincey@gmail.com",
            "444.44.444.444",
            -0.109803,
            51.502293
    );

    // diff is 1.425 KM
    public static final User centralLondonUser3 = new User(
            5,
            "Steven",
            "Toast",
            "toastoflondon@tesco.net",
            "555.55.555.555",
            -0.116573,
            51.518145
    );

    // Outer London Users - people who do not live close to the central point but still within the 50 mile radius

    // coordinates 51.4094913, -1.2758247
    // should be 49.796687 miles from centre
    public static final User outerLondonUser1 = new User(
            6,
            "Ray",
            "Purchase",
            "purchaseray@tesco.net",
            "666.66.666.666",
            -1.2758247,
            51.4094913
    );

    public static List<User> basicStubbedUsers() {
        return List.of(stubUserJeff, stubUserBill);
    }

    public static List<User> centralLondonUsers() {
        return List.of(centralLondonUser1, centralLondonUser2, centralLondonUser3, outerLondonUser1);
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String basicStubbedUsersJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(basicStubbedUsers());
    }

    public static String centralLondonUsersJson() throws JsonProcessingException {
        return objectMapper.writeValueAsString(centralLondonUsers());
    }

}
