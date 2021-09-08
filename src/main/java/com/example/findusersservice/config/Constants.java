package com.example.findusersservice.config;

public class Constants {

    /**
     * API Constants
     */

    public static final String USERS_LONDON_PATH_V1 = "/v1/users/london";
    public static final String APPLICATION_JSON = "application/json";
    public static final String API_TITLE = "Find Users Service";
    public static final String API_VERSION = "0.1.0";
    public static final String API_DESCRIPTION = "API that returns all users who are listed as either living in London, or whose current coordinates are within 50 miles of London.";
    public static final String API_TAG = "Find User";

    /**
     * Formula Constants
     */

    public static final double searchAreaMiles = 50;
    public static final double centreOfLondonLatitude = 51.5074;
    public static final double centreOfLondonLongitude = -0.1278;
    public static final double earthRadiusMiles = 3958.8;

    /**
     * Error Constants
     */

    public static final String INTERNAL_SERVER_ERROR = "500 Internal Server Error from GET";
    public static final String NOT_FOUND = "404 Not Found from GET";
    public static final String JSON_DECODING_ERROR = "JSON decoding error";

    public static final String PROBLEM_UPSTREAM = "Problem with upstream services.";
    public static final String UNEXPECTED_RESPONSE = "Unexpected Response Received from upstream services.";
    public static final String UNEXPECTED_ERROR = "An unexpected error occurred, please try again later.";

    /**
     * Suppress default constructor for noninstantiability
     */

    private Constants() {
        throw new AssertionError();
    }

}
