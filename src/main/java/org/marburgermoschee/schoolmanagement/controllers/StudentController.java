package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.RegisterStudentRequest;
import org.marburgermoschee.schoolmanagement.dtos.StudentDto;
import org.marburgermoschee.schoolmanagement.entities.Parent;
import org.marburgermoschee.schoolmanagement.entities.Student;
import org.marburgermoschee.schoolmanagement.exceptions.UserNotFoundException;
import org.marburgermoschee.schoolmanagement.mappers.StudentMapper;
import org.marburgermoschee.schoolmanagement.repositories.ParentRepository;
import org.marburgermoschee.schoolmanagement.repositories.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final ParentRepository parentRepository;

    @PostMapping
    public StudentDto register(@Valid @RequestBody RegisterStudentRequest request) {
        Student student = studentMapper.toEntity(request);
        Parent parent = parentRepository.findById(request.getParentId()).orElseThrow(
                () -> new UserNotFoundException("Parent not found"));
        student.setParent(parent);
        studentRepository.save(student);
        return studentMapper.toDto(student);

    }

    @GetMapping
    public List<StudentDto>getAllStudents(){
        List<Student> students = studentRepository.getAll();
        return students.stream().map(studentMapper::toDto).toList();
    }

}
