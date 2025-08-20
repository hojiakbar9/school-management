package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.*;
import org.marburgermoschee.schoolmanagement.entities.Class;
import org.marburgermoschee.schoolmanagement.entities.Role;
import org.marburgermoschee.schoolmanagement.entities.Teacher;
import org.marburgermoschee.schoolmanagement.entities.User;
import org.marburgermoschee.schoolmanagement.exceptions.DuplicateEntryException;
import org.marburgermoschee.schoolmanagement.exceptions.EntityNotFoundException;
import org.marburgermoschee.schoolmanagement.mappers.ClassMapper;
import org.marburgermoschee.schoolmanagement.mappers.UserMapper;
import org.marburgermoschee.schoolmanagement.repositories.ClassRepository;
import org.marburgermoschee.schoolmanagement.repositories.TeacherRepository;
import org.marburgermoschee.schoolmanagement.repositories.UserRepository;
import org.marburgermoschee.schoolmanagement.services.PasswordGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;
    private final TeacherRepository teacherRepository;
    private final ClassMapper classMapper;
    private final ClassRepository classRepository;

    @GetMapping
    public List<UserDto> getTeachers(){
        List<Teacher> teachers = teacherRepository.getAll();
        return teachers.stream().map(
                teacher -> userMapper.toDto(teacher.getUser())).toList();
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
        if (userRepository.existsUserByEmail(registerTeacherRequest.getEmail()))
            throw new DuplicateEntryException("Email already exists");
        User user = userMapper.registerTeacher(registerTeacherRequest);
        user.setPassword(passwordGenerator.generatePassword());
        user.setRole(Role.TEACHER);
        userRepository.save(user);

        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacherRepository.save(teacher);

        UserDto userDto = userMapper.toDto(teacher.getUser());
        URI uri = builder.path("/teachers/{id}").buildAndExpand(userDto.getId()).toUri();
        return ResponseEntity.created(uri).body(userDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateTeacher(
            @PathVariable Integer id,
            @Valid @RequestBody UpdateTeacherRequest request
    ){
        Teacher teacher = teacherRepository.getTeacher(id).orElseThrow(
                () -> new EntityNotFoundException("Teacher not found"));
        User updated = userMapper.updateTeacher(request, teacher.getUser());
        updated.setEmail(teacher.getUser().getEmail());
        updated.setPassword(teacher.getUser().getPassword());
        userRepository.save(updated);
        return ResponseEntity.ok(userMapper.toDto(updated));
    }
    @GetMapping("/{id}/classes")
    public List<ClassDto> classesTaught(@PathVariable Integer id){
        Teacher teacher = teacherRepository
                .getTeacherWithClasses(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        Set<Class> classes = teacher.getClasses();
        return classes.stream().map(classMapper::toDto).toList();
    }

    @PostMapping("/{id}")
    public ResponseEntity<ClassDto> assignClass(@PathVariable Integer id, @Valid @RequestBody AssignClassRequest request){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        Class cl = classRepository.getClassWithTeachers(request.getClassId())
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));
        cl.addTeacher(teacher);
        classRepository.save(cl);
        return ResponseEntity.status(HttpStatus.CREATED).body(classMapper.toDto(cl));
    }
}
