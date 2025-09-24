package org.marburgermoschee.schoolmanagement.services;

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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordGenerator passwordGenerator;
    private final ClassMapper classMapper;
    private final ClassRepository classRepository;

    public List<UserDto> getTeachers(){
        List<Teacher> teachers = teacherRepository.getAll();
        return teachers.stream().map(
                teacher -> userMapper.toDto(teacher.getUser())).toList();
    }

    public UserDto getTeacher(RegisterTeacherRequest registerTeacherRequest){
        if (userRepository.existsUserByEmail(registerTeacherRequest.getEmail()))
            throw new DuplicateEntryException("Email already exists");
        User user = userMapper.registerTeacher(registerTeacherRequest);
        user.setPassword(passwordGenerator.generatePassword());
        user.setRole(Role.TEACHER);
        userRepository.save(user);

        Teacher teacher = new Teacher();
        teacher.setUser(user);
        teacherRepository.save(teacher);

        return userMapper.toDto(teacher.getUser());
    }
    public UserDto updateTeacher(Integer id, UpdateTeacherRequest request){
        Teacher teacher = teacherRepository.getTeacher(id).orElseThrow(
                () -> new EntityNotFoundException("Teacher not found"));
        User updated = userMapper.updateTeacher(request, teacher.getUser());
        updated.setEmail(teacher.getUser().getEmail());
        updated.setPassword(teacher.getUser().getPassword());
        userRepository.save(updated);
        return userMapper.toDto(updated);
    }

    public List<ClassDto> getClassesTaught(Integer id){
        Teacher teacher = teacherRepository
                .getTeacherWithClasses(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        Set<Class> classes = teacher.getClasses();
        return classes.stream().map(classMapper::toDto).toList();
    }
    public ClassDto assignClass(Integer id, AssignClassRequest request){
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Teacher not found"));
        Class cl = classRepository.getClassWithTeachers(request.getClassId())
                .orElseThrow(() -> new EntityNotFoundException("Class not found"));
        cl.addTeacher(teacher);
        classRepository.save(cl);
        return classMapper.toDto(cl);

    }
}
