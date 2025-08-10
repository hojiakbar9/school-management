package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.*;
import org.marburgermoschee.schoolmanagement.entities.Role;
import org.marburgermoschee.schoolmanagement.entities.Teacher;
import org.marburgermoschee.schoolmanagement.entities.User;
import org.marburgermoschee.schoolmanagement.exceptions.DuplicateEmailException;
import org.marburgermoschee.schoolmanagement.exceptions.EntityNotFoundException;
import org.marburgermoschee.schoolmanagement.mappers.UserMapper;
import org.marburgermoschee.schoolmanagement.services.PasswordGenerator;
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
    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;
    private final TeacherRepository teacherRepository;

    @GetMapping
    public List<UserDto> getTeachers(){
        List<Teacher> teachers = teacherRepository.getAll();
        return teachers.stream().map(
                teacher -> userMapper.toDto(teacher.getUser())).toList();
    }
    @GetMapping("/{id}")
    public UserDto getParent(@PathVariable("id") Integer id){
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
            throw new DuplicateEmailException();
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
    public ResponseEntity<UserDto> updateParent(
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
}
