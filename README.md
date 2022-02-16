# Getting Started

Sample spring boot reactive programming. 

Features:

1. Used RouterFunction instead of RestControllers. 
2. Added Flyway configuration for schema migration
3. r2dbc H2 for database  

### Gradle Build

````
./gradlew clean build -xtest
````

** Note: Unit test case are in-progress. **

### Sample Request to test

Add an employee
````
curl -i -X POST -H 'Content-Type: application/json' -d '{"name": "tanish", "email": "test.gmail", "dob": "09/10/1985"}' http://localhost:8080/rest/api/employee
````

Update an employee
````
curl -i -X PUT -H 'Content-Type: application/json' -d '{"name": "arun", "email": "test@gmail.com", "dob": "09/10/1985"}' http://localhost:8080/rest/api/employee
````

Get all employee
````
curl -i -X GET -H 'Content-Type: application/json' http://localhost:8080/rest/api/employee
````

Get employee by name.

````
curl -i -X GET -H 'Content-Type: application/json' http://localhost:8080/rest/api/employee?name=arun
````

Delete employee by id
````
curl -i -X DELETE -H 'Content-Type: application/json' http://localhost:8080/rest/api/employee/1
````

### Swagger 

Add operationId, tags in the RouterFunction to show operations in swagger.

* [spring-webflux-support](https://springdoc.org/#spring-webflux-support)

````
http://localhost:8080/swagger-ui.html
````

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.5.9/gradle-plugin/reference/html/)
* [Create an OCI image](https://docs.spring.io/spring-boot/docs/2.5.9/gradle-plugin/reference/html/#build-image)
* [Spring Data R2DBC](https://docs.spring.io/spring-boot/docs/2.6.3/reference/html/spring-boot-features.html#boot-features-r2dbc)

### Guides
The following guides illustrate how to use some features concretely:

* [Acessing data with R2DBC](https://spring.io/guides/gs/accessing-data-r2dbc/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)
* [R2DBC Homepage](https://r2dbc.io)

## Missing R2DBC Driver

Make sure to include a [R2DBC Driver](https://r2dbc.io/drivers/) to connect to your database.
