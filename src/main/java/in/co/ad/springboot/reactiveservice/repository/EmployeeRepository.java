package in.co.ad.springboot.reactiveservice.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import in.co.ad.springboot.reactiveservice.entity.Employee;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EmployeeRepository extends ReactiveCrudRepository<Employee, Long> {
    @Query("SELECT * FROM employee WHERE name = :name")
    Flux<Employee> findByName(String name);

    @Query("SELECT * FROM employee WHERE id = :id")
    Mono<Employee> findById(@Parameter(in = ParameterIn.PATH, description = "The employee Id") Long id);
}
