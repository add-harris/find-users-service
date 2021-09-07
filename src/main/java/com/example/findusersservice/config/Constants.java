package com.example.findusersservice.config;

public class Constants {

    /**
     * API Constants
     */

    public static final String USERS_LONDON_PATH_V1 = "/v1/users/london";

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
