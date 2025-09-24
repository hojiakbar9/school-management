package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.*;
import org.marburgermoschee.schoolmanagement.entities.Teacher;
import org.marburgermoschee.schoolmanagement.exceptions.EntityNotFoundException;
import org.marburgermoschee.schoolmanagement.mappers.UserMapper;
import org.marburgermoschee.schoolmanagement.repositories.TeacherRepository;
import org.marburgermoschee.schoolmanagement.services.TeacherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
    private final UserMapper userMapper;
    private final TeacherRepository teacherRepository;
    private final TeacherService teacherService;

    @GetMapping
    public List<UserDto> getTeachers(){
        return teacherService.getTeachers();
    }
    @GetMapping("/{id}")
    public UserDto getTeacher(@PathVariable("id") Integer id){
        Teacher teacher = teacherRepository.getTeacher(id).orElseThrow(
                () ->  new EntityNotFoundException("Teacher not found"));
        return userMapper.toDto(teacher.getUser());
    }

    @PostMapping
    @Transactional
    public ResponseEntity<UserDto> registerTeacher(
            @Valid @RequestBody RegisterTeacherRequest registerTeacherRequest,
            UriComponentsBuilder builder
    ){

        UserDto userDto = teacherService.getTeacher(registerTeacherRequest);
        URI uri = builder.path("/teachers/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public UserDto updateTeacher(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateTeacherRequest request
    ){
        return teacherService.updateTeacher(id, request);
    }
    @GetMapping("/{id}/classes")
    public List<ClassDto> getClassesTaught(@PathVariable Integer id){
        return teacherService.getClassesTaught(id);
    }

    @PostMapping("/{id}")
    public ResponseEntity<ClassDto> assignClass(@PathVariable Integer id, @Valid @RequestBody AssignClassRequest request){
        ClassDto cl = teacherService.assignClass(id, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(cl);
    }
}
