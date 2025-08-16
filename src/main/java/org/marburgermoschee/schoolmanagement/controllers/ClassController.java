package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.ClassDto;
import org.marburgermoschee.schoolmanagement.dtos.ClassTeacherDto;
import org.marburgermoschee.schoolmanagement.dtos.ClassWithTeachersDto;
import org.marburgermoschee.schoolmanagement.dtos.RegisterNewClassRequest;
import org.marburgermoschee.schoolmanagement.entities.Class;
import org.marburgermoschee.schoolmanagement.mappers.ClassMapper;
import org.marburgermoschee.schoolmanagement.repositories.ClassRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
               .map(cl -> {
                var dto =   new ClassWithTeachersDto();
                dto.setId(cl.getId());
                dto.setType(cl.getType());
                dto.setTeachers(
                        cl.getTeachers().stream()
                                .map(teacher -> new ClassTeacherDto(teacher.getUser().getFirstName())).toList());
                return dto;
               })
               .toList();

    }
}
