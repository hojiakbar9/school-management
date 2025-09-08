package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.*;
import org.marburgermoschee.schoolmanagement.entities.Class;
import org.marburgermoschee.schoolmanagement.mappers.ClassMapper;
import org.marburgermoschee.schoolmanagement.mappers.LessonMapper;
import org.marburgermoschee.schoolmanagement.repositories.ClassRepository;
import org.marburgermoschee.schoolmanagement.services.ClassService;
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
    private final LessonMapper lessonMapper;
    private final ClassService classService;

    @PostMapping
    public ResponseEntity<ClassDto> createClass(
            @Valid @RequestBody RegisterNewClassRequest request,
            UriComponentsBuilder builder
    ){
        Class newClass = classService.createClass(request);
        URI uri = builder.path("/classes/{id}").buildAndExpand(newClass.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
    @GetMapping
    public List<ClassWithTeachersDto> getAllClassesWithTeachers(){
    return classService.getAllClassesWithTeachers();
    }
    @GetMapping("/{id}")
    public ClassWithTeachersDto getAllClassesWithTeachers(@PathVariable Integer id){
        return classService.getClassWithTeachers(id);
    }

    @GetMapping("/{id}/students")
    public List<ClassStudentDto> getClassStudents(@PathVariable Integer id){
        return classService.getStudentsOfClass(id);
    }
    @GetMapping("/{id}/lessons")
    public List<LessonDto> getClassLessons(@PathVariable Integer id){
        return classService.getClassLessons(id);
    }


    @PostMapping("/{id}/lessons")
    public void createLesson(@PathVariable Integer id,@Valid @RequestBody RegisterNewLessonRequest request){
        classService.createLesson(id, request);
    }

}
