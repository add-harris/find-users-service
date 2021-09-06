# Find Users Service

An API that returns all users who are listed as either living in London, or whose current coordinates are within 50 miles of London.

## Running the Application

Requirements:
- Java 11
- Apache Maven 3.3 or above

To run use command:

```shell
mvn spring-boot:run
```

The application will then be available at `http://localhost:8080/find-users`


## Centre of London

For the purpose of this exercise the centre of London has been 
taken as the statue of King Charles I to the south of Trafalgar Square, 
with coordinates of approximately `51.5074° N, 0.1278° W` 
(referenced from: [google](what are the coordinates for the center of london),
[batterseapowerstation](https://batterseapowerstation.co.uk/news/article/where-is-the-centre-of-london-) and 
[distancesto.com](https://www.distancesto.com/coordinates/gb/central-london-latitude-longitude/history/108706.html))