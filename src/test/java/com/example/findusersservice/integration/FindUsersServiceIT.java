package com.example.findusersservice.integration;

import com.example.findusersservice.models.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static com.example.findusersservice.utils.TestFixtures.*;


public class FindUsersServiceIT extends IntegrationTest {

    @Test
    void application_returns_ok_response_on_GET() throws Exception {
        stubCityEndpoint();
        stubUserEndpoint();

        callApplication()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void application_returns_users_retrieved_from_city_endpoint() throws Exception {
        stubCityEndpointWithResponse(cityEndpointUsersJson());
        stubUserEndpoint();

        callApplication()
                .expectStatus()
                .isOk()
                .expectBodyList(User.class)
                .contains(cityEndpointUser1)
                .contains(cityEndpointUser2);
    }

    @Test
    void application_only_returns_users_within_range_from_user_endpoint() throws Exception {
        stubCityEndpoint();
        stubUserEndpointWithResponse(allInsideAndOutsideAreaUsersJson());

        callApplication()
                .expectStatus()
                .isOk()
                .expectBodyList(User.class)
                .contains(centralLondonUser1)
                .contains(centralLondonUser2)
                .contains(centralLondonUser3)
                .contains(outerLondonUser1)
                .contains(outerLondonUser2)
                .contains(outerLondonUser3)
                .contains(outerLondonUser4);
    }

    @Test
    void application_does_not_return_users_out_of_range_from_user_endpoint() throws Exception {
        stubCityEndpoint();
        stubUserEndpointWithResponse(allInsideAndOutsideAreaUsersJson());

        callApplication()
                .expectStatus()
                .isOk()
                .expectBodyList(User.class)
                .doesNotContain(outsideAreaUser1)
                .doesNotContain(outsideAreaUser2)
                .doesNotContain(outsideAreaUser3)
                .doesNotContain(outsideAreaUser4)
                .doesNotContain(northernIrelandUser)
                .doesNotContain(scotlandUser)
                .doesNotContain(southWestUser)
                .doesNotContain(icelandUser)
                .doesNotContain(indiaUser)
                .doesNotContain(newZealandUser);
    }

    /**
     * This test verifies the application gets users from both the city endpoint and the user endpoint,
     * filters users from the user endpoint for only those withing range, then returns a combined list.
     */
    @Test
    void application_full_test() throws Exception {
        stubCityEndpointWithResponse(cityEndpointUsersJson());
        stubUserEndpointWithResponse(allInsideAndOutsideAreaUsersJson());

        callApplication()
                .expectStatus()
                .isOk()
                .expectBodyList(User.class)
                .contains(cityEndpointUser1)
                .contains(cityEndpointUser2)
                .contains(centralLondonUser1)
                .contains(centralLondonUser2)
                .contains(centralLondonUser3)
                .contains(outerLondonUser1)
                .contains(outerLondonUser2)
                .contains(outerLondonUser3)
                .contains(outerLondonUser4)
                .doesNotContain(outsideAreaUser1)
                .doesNotContain(outsideAreaUser2)
                .doesNotContain(outsideAreaUser3)
                .doesNotContain(outsideAreaUser4)
                .doesNotContain(northernIrelandUser)
                .doesNotContain(scotlandUser)
                .doesNotContain(southWestUser)
                .doesNotContain(icelandUser)
                .doesNotContain(indiaUser)
                .doesNotContain(newZealandUser);
    }

}
