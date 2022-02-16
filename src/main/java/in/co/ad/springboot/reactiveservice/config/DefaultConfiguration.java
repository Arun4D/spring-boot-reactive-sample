package in.co.ad.springboot.reactiveservice.config;

import static org.springdoc.core.fn.builders.apiresponse.Builder.responseBuilder;
import static org.springdoc.core.fn.builders.parameter.Builder.parameterBuilder;
import static org.springdoc.core.fn.builders.requestbody.Builder.requestBodyBuilder;
import static org.springdoc.webflux.core.fn.SpringdocRouteBuilder.route;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import in.co.ad.springboot.reactiveservice.entity.Employee;
import in.co.ad.springboot.reactiveservice.handler.EmployeeHandler;
import io.r2dbc.spi.ConnectionFactory;
import io.swagger.v3.oas.annotations.enums.ParameterIn;

@Configuration
public class DefaultConfiguration {

    @Bean
    public ConnectionFactoryInitializer initializer(
            @Qualifier("connectionFactory") ConnectionFactory connectionFactory) {
        var initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(new ResourceDatabasePopulator(new ClassPathResource("schema.sql")));

        return initializer;
    }

    @Bean
    public RouterFunction<ServerResponse> employeeRoute(EmployeeHandler employeeHandler) {
      /* return RouterFunctions.route()
      .GET("/rest/api/employee", RequestPredicates.accept(MediaType.APPLICATION_JSON), employeeHandler::find)
      .GET("/rest/api/employee/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), employeeHandler::findById)
      .POST("/rest/api/employee", RequestPredicates.accept(MediaType.APPLICATION_JSON), employeeHandler::save)
      .PUT("/rest/api/employee", RequestPredicates.accept(MediaType.APPLICATION_JSON), employeeHandler::update)
      .DELETE("/rest/api/employee/{id}", RequestPredicates.accept(MediaType.APPLICATION_JSON), employeeHandler::delete)
      .build(); */

      return route()
              .GET("/rest/api/employee", accept(APPLICATION_JSON), employeeHandler::find,
                      ops -> ops
                              .tag("employee")
                              .operationId("findAllEmployee").summary("Find all employee")
                              .tags(new String[] { "employee" })
                              .response(responseBuilder().responseCode("200").description("successful operation")
                                      .implementation(Employee.class))
                              .response(
                                      responseBuilder().responseCode("400").description("Invalid request supplied"))
                              .response(responseBuilder().responseCode("404").description("Employees not found")))
              .build()
              .and(route()
                      .GET("/rest/api/employee/{id}", accept(APPLICATION_JSON), employeeHandler::findById,
                              ops -> ops
                                      .tag("employee")
                                      .operationId("findEmployeeById").summary("Find employee by ID")
                                      .tags(new String[] { "employee" })
                                      .parameter(parameterBuilder().in(ParameterIn.PATH).name("id")
                                              .description("Employee Id"))
                                      .response(
                                              responseBuilder().responseCode("200").description("successful operation")
                                                      .implementation(Employee.class))
                                      .response(
                                              responseBuilder().responseCode("400")
                                                      .description("Invalid Employee ID supplied"))
                                      .response(
                                              responseBuilder().responseCode("404").description("Employee not found")))
                      .build())
              .and(route().POST("/rest/api/employee", accept(APPLICATION_JSON), employeeHandler::save,
                      ops -> ops
                              .tag("employee")
                              .operationId("saveEmployee").summary("Save employee")
                              .tags(new String[] { "employee" })
                              .requestBody(requestBodyBuilder().implementation(Employee.class))
                              .response(responseBuilder().responseCode("200").description("successful operation")
                                      .implementation(Employee.class))
                              .response(
                                      responseBuilder().responseCode("400")
                                              .description("Invalid Employee details supplied")))
                      .build())
              .and(route().PUT("/rest/api/employee", accept(APPLICATION_JSON), employeeHandler::update,
                      ops -> ops
                              .tag("employee")
                              .operationId("updateEmployee").summary("Update employee")
                              .tags(new String[] { "employee" })
                              .requestBody(requestBodyBuilder().implementation(Employee.class))
                              .response(responseBuilder().responseCode("200").description("successful operation")
                                      .implementation(Employee.class))
                              .response(
                                      responseBuilder().responseCode("400")
                                              .description("Invalid Employee details supplied")))
                      .build())
              .and(route().DELETE("/rest/api/employee/{id}", accept(APPLICATION_JSON), employeeHandler::delete,
                      ops -> ops
                              .operationId("deleteBy").description("Delete By Id").tags(new String[] { "employee" })
                              .parameter(parameterBuilder().in(ParameterIn.PATH).name("id"))
                              .response(responseBuilder().responseCode("200")
                                      .content(org.springdoc.core.fn.builders.content.Builder.contentBuilder())))
                      .build());
    }
}
