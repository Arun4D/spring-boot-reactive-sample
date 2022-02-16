package in.co.ad.springboot.reactiveservice.handler;

import java.util.Objects;
import java.util.Optional;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import in.co.ad.springboot.reactiveservice.entity.Employee;
import in.co.ad.springboot.reactiveservice.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class EmployeeHandler {
    private final EmployeeRepository employeeRepository;

    public EmployeeHandler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Mono<ServerResponse> find(ServerRequest request) {
        Optional<String> name = request.queryParam("name");
        Flux<Employee> emp = Flux.empty();
        if (name.isPresent()) {
            log.info("Value available: " + name.get());
            emp = employeeRepository.findByName(name.get());
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(emp, Employee.class);
        } else {
            log.info("Value unavailable");
            return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
                    .body(employeeRepository.findAll(), Employee.class);
        }

    }

    public Mono<ServerResponse> findById(ServerRequest request) {

        Long id = Long.valueOf(request.pathVariable("id"));

        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(employeeRepository.findById(id),
                Employee.class);
    }

    public Mono<ServerResponse> save(ServerRequest request) {

        Mono<Employee> employee = request.bodyToMono(Employee.class);

        employee = employee.flatMap(emp -> employeeRepository.save(emp));

        return ServerResponse.ok().body(employee, Employee.class);
    }

    public Mono<ServerResponse> update(ServerRequest request) {

        Mono<Employee> employee = request.bodyToMono(Employee.class);

        employee = employee.flatMap(emp -> employeeRepository.save(emp));

        return ServerResponse.ok().body(employee, Employee.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {

        Long id = Long.valueOf(request.pathVariable("id"));

        final Mono<Employee> emp = employeeRepository.findById(id);
        if (Objects.isNull(emp)) {
            return Mono.empty();
        }
        return ServerResponse.ok().body(emp.flatMap(empToBeDeleted -> employeeRepository
                .delete(empToBeDeleted).then(Mono.just(empToBeDeleted))), Employee.class);

    }

}
