REST APIs powered by Spring Boot
==================================

1. This project is a simple REST application for serving a entity (target in this example).

    1.1 It uses Spring Boot, Spring Data JPA and some other helper libraries like Mockito, MockMvc etc.
    1.2 It also makes extensive use of provided annotations by spring.

2. The application serve the following requests:-

    2.1 retrieve target entity details by executing GET request at `/target/retrieve/{id}`, where `{id}` is a target identifier.
    2.2 update target entity information by executing POST request at `/target/update/{id}`, where `{id}` is a target identifier and request body must have an `information` attribute, which must exceed 4MB in size.

3. To run the project:-

    3.1 Import the code in your favourite ide
    3.2 run command `mvn spring-boot:run`
    3.3 for further information please see: `https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html`
