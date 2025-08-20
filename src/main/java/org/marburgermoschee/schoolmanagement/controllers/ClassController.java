package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.*;
import org.marburgermoschee.schoolmanagement.entities.Class;
import org.marburgermoschee.schoolmanagement.entities.Student;
import org.marburgermoschee.schoolmanagement.exceptions.EntityNotFoundException;
import org.marburgermoschee.schoolmanagement.mappers.ClassMapper;
import org.marburgermoschee.schoolmanagement.repositories.ClassRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("classes")
    public class ClassController {

    private final ClassMapper classMapper;
    private final ClassRepository classRepository;

    @PostMapping
    public ResponseEntity<ClassDto> createClass(
            @Valid @RequestBody RegisterNewClassRequest request,
            UriComponentsBuilder builder
    ){
        Class newClass = classMapper.register(request);
        System.out.println(newClass.getType());
        classRepository.save(newClass);
        URI uri = builder.path("/classes/{id}").buildAndExpand(newClass.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
    @GetMapping
    public List<ClassWithTeachersDto> getClassWithTeachers(){
       List<Class> classes =  classRepository.getAllWithTeachers();
       return classes.stream()
               .map(classMapper::toClassWithTeachersDto)
               .toList();

    }
    @GetMapping("/{id}")
    public ClassWithTeachersDto getClassWithTeachers(@PathVariable Integer id){
        Class cl = classRepository.getClassWithTeachers(id)
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));
        return classMapper.toClassWithTeachersDto(cl);
    }
    @GetMapping("/{id}/students")
    public List<ClassStudentDto> getClassStudents(@PathVariable Integer id){
        Class cl = classRepository.getClassWithStudents(id)
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));

        Set<Student> students = cl.getStudents();
        return students.stream().map(classMapper::toClassStudentDto).toList();
    }


}
