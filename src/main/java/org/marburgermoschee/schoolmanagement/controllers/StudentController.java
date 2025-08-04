package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.RegisterStudentRequest;
import org.marburgermoschee.schoolmanagement.dtos.StudentDto;
import org.marburgermoschee.schoolmanagement.entities.Parent;
import org.marburgermoschee.schoolmanagement.entities.Student;
import org.marburgermoschee.schoolmanagement.exceptions.StudentNotFoundException;
import org.marburgermoschee.schoolmanagement.exceptions.UserNotFoundException;
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
    private final ParentRepository parentRepository;

    @PostMapping
    public StudentDto register(
            @Valid @RequestBody RegisterStudentRequest request)
    {
        Student student = studentMapper.toEntity(request);
        Integer parentId = request.getParentId();
        if(parentId != null){
            var parent =  parentRepository
                    .findById(parentId)
                    .orElseThrow(() -> new UserNotFoundException("Parent not found"));
            student.setParent(parent);
        }
        studentRepository.save(student);
        var dto = studentMapper.toDto(student);
        dto.setParentId(parentId);
        return dto;
    }

    @GetMapping
    public List<StudentDto> getStudents() {
       var students = studentRepository.getAll();
       return students.stream().map(student -> {
           var dto = studentMapper.toDto(student);
           if (student.hasParent()) {
               dto.setParentId(student.getParent().getId());
           }
           return dto;
       }).toList();
    }
    @GetMapping("/{id}")
    public StudentDto getStudentById(@PathVariable Integer id) {
        var student =  studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        var dto = studentMapper.toDto(student);
        if (student.hasParent()) {
            dto.setParentId(student.getParent().getId());
        }
        return dto;
    }
    @PutMapping("/{id}")
    public StudentDto updateStudent(@PathVariable Integer id,
                                    @Valid @RequestBody RegisterStudentRequest registerStudentRequest) {
        var student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        Student studentUpdated = studentMapper.update(registerStudentRequest,  student);
        if (!student.hasParent() && registerStudentRequest.getParentId() != null) {
            Parent parent = parentRepository
                    .findById(registerStudentRequest.getParentId())
                    .orElseThrow(() -> new UserNotFoundException("Parent not found"));
            studentUpdated.setParent(parent);
        }
        studentRepository.save(studentUpdated);

        StudentDto dto = studentMapper.toDto(studentUpdated);
        dto.setParentId(studentUpdated.getParent().getId());
        return dto;
    }

}
