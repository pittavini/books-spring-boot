# books-spring-boot

The project can be run using the maven command:

mvn spring-boot:run

There is a swagger endpoint that is run within the project for testing purposes with the url:

http://localhost:8080/swagger-ui.html

There are unit tests for both repository and rest controller, these can be run using the command:

mvn clean test

TODO Items:
- collect code coverage reports from UT
- RestController UT missing security testing, and delete api testing
- User should be able to pass the limit and direction order to findall api