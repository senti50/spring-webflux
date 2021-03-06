package com.example.springwebflux;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class StudentController {

    private Flux<Student> studentFlux;

    public StudentController() {
        studentFlux = Flux.just(
                new Student("Jan", "Nowak"),
                new Student("Bartek", "Kowalski"),
                new Student("Janusz", "Heles"),
                new Student("Maryla", "Rodowicz")
        );
    }

    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Student> getStudents() {
        return studentFlux.delayElements(Duration.ofSeconds(2));
    }

    @PostMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Student> addStudents(@RequestBody Student student) {
        studentFlux = this.studentFlux.mergeWith(Mono.just(student));
        return studentFlux;
    }
}
