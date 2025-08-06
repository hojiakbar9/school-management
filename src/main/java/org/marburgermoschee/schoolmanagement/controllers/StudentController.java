package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.StudentDto;
import org.marburgermoschee.schoolmanagement.entities.Student;
import org.marburgermoschee.schoolmanagement.mappers.StudentMapper;
import org.marburgermoschee.schoolmanagement.repositories.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;


    @GetMapping
    @Transactional
    public List<StudentDto>getAllStudents(){
        List<Student> students = studentRepository.getAll();
        return students.stream().map(studentMapper::toDto).toList();
    }

}
