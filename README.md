# Find Users Service

An API that returns all users who are listed as either living in London, or whose current coordinates are within 50 miles of London.

This application has been built using 
[Maven](http://maven.apache.org/),
[Spring-Boot](https://spring.io/projects/spring-boot) (veriosn 2.5.4), 
project [Lombok](https://projectlombok.org/), and 
[WebFlux](https://docs.spring.io/spring-framework/docs/current/reference/html/web-reactive.html) (Spring Boots reactive HTTP client).

## Running the Application

Requirements:
- Java 11
- Apache Maven 3.3 or above

To run the application use command:

```shell
mvn spring-boot:run
```

The application will then be available at `http://localhost:8080/find-users`

## Usage

To hit the API make a http `GET` request to path `/v1/users/london`.

### Example Request

```shell
curl http://localhost:8080/find-users/v1/users/london
```
### Example Response

```json
[
    {
        "id": 1,
        "email": "jeffgold@hotmail.com",
        "longitude": -0.007956,
        "latitude": 51.535055,
        "first_name": "Jeff",
        "last_name": "GoldBlum",
        "ip_address": "111.11.111.111"
    },
    {
        "id": 2,
        "email": "billmurr@gmail.com",
        "longitude": -0.043999,
        "latitude": 51.529989,
        "first_name": "Bill",
        "last_name": "Murray",
        "ip_address": "222.22.222.222"
    }
]
```

## Testing

### Unit Tests

To run unit tests use command:
```shell
mvn clean test
```

### Integration Tests

To run integration tests use command:
```shell
mvn clean verify
```

## Key Features

### WebFlux

As Springs RestTemplate is now deprecated, this application makes use of Springs WebFlux,
a new reactive HTTP client able to make HTTP asynchronously.

### SpringDoc and Swagger-UI

This project is self documenting and features a very simplistic implementation of
OpenApi 3 specifications using [SpringDoc OpenApi](https://springdoc.org/).

Swagger Ui is available by running the application and going to: 
- http://localhost:8080/find-users/swagger-ui.html

The API specification is also available in both JSON and YAML formats at:
- http://localhost:8080/find-users/v3/api-docs
- http://localhost:8080/find-users/v3/api-docs.yaml

### Self Contained Integration Testing

This repo includes a full set of integrations test that can be ran in memory, 
without any external dependencies. These tests are constructed with
[Spring-Boot-Test](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-test),
[WireMock](http://wiremock.org/), Springs
[WebTestClient](https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/test/web/reactive/server/WebTestClient.html),
and [Mavens Failsafe plugin](https://maven.apache.org/surefire/maven-failsafe-plugin/).

Heres how it works:
- The tests use Spring-Boots `@SpringBootTest` annotation to load the whole application and start it
  on a random available port.
- WebClientTest, which is a wrapper for WebClient, is then used to make real http requests to the running application.
- WireMock is used to spin up an in-memory stub server running on a separate port,
  and is configured to return stubbed responses.
- For the purposes of the tests the application is configured to make its backend requests to the
  in-memory stubbed server instead of the real backend endpoints, allowing almost full end-to-end testing
  without the need of any external environment or dependencies.
- The tests are ran using Maven's Failsafe plugin, a plugin designed for longer running integration tests,
  and is configured to pick any tests in the `src/test/java/com/example/findusersservice/integration`
  directory ending in `IT`.

## Criteria and Assumptions

The Criteria for this assignment has been given as follows:

_**An API that returns all users who are listed as either living in London, or whose current 
coordinates are within 50 miles of London.**_

As there is no way to clarify these requirements, several assumptions have been made 
for the purpose of this exercise:
- "_either living in London, or whose current coordinates are within 50 miles of London._" has been taken to mean 
  a single API that returns all users who fit either criteria in one shot, not two seperate API's.
- "_whose current coordinates are within 50 miles of London_" has been taken
    to mean 50 miles from the **centre of London**, not 50 miles from the perimeter of London or 
  the surrounding area.
- The centre of London has been taken as the statue of King Charles I 
  to the south of Trafalgar Square, coordinates `51.5074° N, 0.1278° W` 
(referenced by: [google](https://www.google.com/search?q=what+are+the+coordinates+for+the+center+of+london&safe=active&ei=5sc4Ye7OO4bJgAbVxZuQDw&oq=what+are+the+coordinates+for+the+center+of+london&gs_lcp=Cgdnd3Mtd2l6EAMyCAghEBYQHRAeMggIIRAWEB0QHjIICCEQFhAdEB4yCAghEBYQHRAeSgQIQRgAUMCYIFjAmCBg8ZogaABwAngAgAFoiAFokgEDMC4xmAEAoAECoAEBwAEB&sclient=gws-wiz&ved=0ahUKEwju2ZDCyu_yAhWGJMAKHdXiBvIQ4dUDCA8&uact=5),
[batterseapowerstation](https://batterseapowerstation.co.uk/news/article/where-is-the-centre-of-london-) and 
[distancesto.com](https://www.distancesto.com/coordinates/gb/central-london-latitude-longitude/history/108706.html))