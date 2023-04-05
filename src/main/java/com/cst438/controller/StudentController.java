package com.cst438.controller;

import com.cst438.domain.EnrollmentRepository;
import com.cst438.domain.Student;
import com.cst438.domain.StudentRepository;
import com.cst438.service.GradebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@CrossOrigin(origins = {"http://localhost:3306", "https://registerf-cst438.herokuapp.com/"})
public class StudentController {
    @Autowired
    StudentRepository studentRepository;

    @Autowired
    GradebookService gradebookService;

    @PostMapping("/student")
    public void addStudent(@RequestParam("email") String email, @RequestParam("name") String name){
        System.out.println("Add student request");
        Student student = new Student(email, name);

        if(studentRepository.findByEmail(email) == null){
            studentRepository.save(student);
        }
    }

    @PostMapping("/student/hold")
    public void changeHoldStatus(@RequestParam("email") String email, @RequestParam("code") int code){
        Student student = studentRepository.findByEmail(email);

        if(student == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No student with given email");
        }

        student.setStatusCode(code);

        studentRepository.save(student);
    }
}
