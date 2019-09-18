REST APIs powered by Spring Boot
==================================

1. This project is a simple REST application for serving a entity (target in this example).

    * It uses Spring Boot, Spring Data JPA and some other helper libraries like Mockito, MockMvc etc.
    * It also makes extensive use of provided annotations by spring.

2. The application serve the following requests:-

    * retrieve target entity details by executing GET request at `/target/retrieve/{id}`, where `{id}` is a target identifier.
    * update target entity information by executing POST request at `/target/update/{id}`, where `{id}` is a target identifier and request body must have an `information` attribute, which must exceed 4MB in size.

3. To run the project:-

    * Import the code in your favourite ide
    * run command `mvn spring-boot:run`
    * for further information please see: `https://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-running-your-application.html`
