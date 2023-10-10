package com.example.demo.dao;

import com.example.demo.model.Student;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class StudentDao {
//    private List<Student> students = new ArrayList<>();
    private Flux<Student> studentFlux = Flux.empty();


//    public List<Student> getStudents(){
//        return students;
//    }

    public Flux<Student> getStudentFlux(){
        return studentFlux;
    }

//    public Student addStudent(String name, String email){
//        UUID id = UUID.randomUUID();
//        Student student = new Student(id, name, email);
//        students.add(student);
//        return student;
//    }

    public Mono<Student> addStudentMono(String name, String email){
        UUID id = UUID.randomUUID();
        Student student = new Student(id, name, email);
        studentFlux = studentFlux.concatWithValues(student);
        return Mono.just(student);
    }


    public Mono<Void> delete(UUID studentId) {
        return studentFlux
                .filter(student -> student.getId().equals(studentId))
                .doOnNext(student -> studentFlux = studentFlux.filter(s -> !s.getId().equals(studentId))) // Remove the matching student from the Flux
                .then(); // Signal completion with Mono<Void>
    }

    public Mono<Student> updateStudent(UUID studentId, String newName, String newEmail) {
        return studentFlux
                .flatMap(student -> {
                    if (student.getId().equals(studentId)) {
                        student.setName(newName);
                        student.setEmail(newEmail);
                        return Mono.just(student);
                    } else {
                        return Mono.just(student);
                    }
                })
                .collectList()
                .flatMap(studentsList -> {
                    if (studentsList.isEmpty()) {
                        return Mono.empty(); // No student with the given ID found
                    } else {
                        studentFlux = Flux.fromIterable(studentsList); // Update the studentFlux with the updated list
                        return Mono.just(studentsList.get(0)); // Return the first updated student
                    }
                });
    }





}
