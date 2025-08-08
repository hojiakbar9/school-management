package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.*;
import org.marburgermoschee.schoolmanagement.entities.Attendance;
import org.marburgermoschee.schoolmanagement.entities.Parent;
import org.marburgermoschee.schoolmanagement.entities.Payment;
import org.marburgermoschee.schoolmanagement.entities.Student;
import org.marburgermoschee.schoolmanagement.exceptions.StudentNotFoundException;
import org.marburgermoschee.schoolmanagement.exceptions.UserNotFoundException;
import org.marburgermoschee.schoolmanagement.mappers.AttendanceMapper;
import org.marburgermoschee.schoolmanagement.mappers.PaymentMapper;
import org.marburgermoschee.schoolmanagement.mappers.StudentMapper;
import org.marburgermoschee.schoolmanagement.repositories.ParentRepository;
import org.marburgermoschee.schoolmanagement.repositories.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final ParentRepository parentRepository;
    private final AttendanceMapper attendanceMapper;
    private final PaymentMapper paymentsMapper;
    @PostMapping
    public StudentDto register(@Valid @RequestBody RegisterStudentRequest request) {
        Student student = studentMapper.toEntity(request);
        Parent parent = parentRepository.findById(request.getParentId()).orElseThrow(
                () -> new UserNotFoundException("Parent not found"));
        student.setParent(parent);
        studentRepository.save(student);
        return studentMapper.toDto(student);
    }
    @PostMapping("/{id}/payments")
    public void recordNewPayment(
            @PathVariable Integer id,
            @Valid @RequestBody RecordPaymentRequest request) {
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        student.addPayment(request.getAmount(), request.getPaymentDate());
        studentRepository.save(student);
    }

    @GetMapping
    public List<StudentDto>getAllStudents(){
        List<Student> students = studentRepository.getAllWithParents();
        return students.stream().map(studentMapper::toDto).toList();
    }

    @GetMapping("/{id}")
    public StudentDto getStudent(@PathVariable Integer id){
        Student student = studentRepository
                .getStudentWithParent(id)
                .orElseThrow(StudentNotFoundException::new);
        return studentMapper.toDto(student);
    }

    @GetMapping("/{id}/attendances")
    public List<AttendanceDto> getAttendances(@PathVariable Integer id){
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        Set<Attendance> attendances = student.getAttendances();
        return attendances.stream().map(attendanceMapper::toDto).toList();
    }
    @GetMapping("{id}/payments")
    public List<PaymentDto> getPayments(@PathVariable Integer id){
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        Set<Payment> payments = student.getPayments();
        return payments.stream().map(paymentsMapper::toDto).toList();
    }
    @PutMapping("/{id}")
    public StudentDto updateStudent(
            @Valid @RequestBody RegisterStudentRequest request,
            @PathVariable Integer id) {
        Student student = studentRepository.getStudentWithParent(id).orElseThrow(StudentNotFoundException::new);
        var parent =  parentRepository.findById(request.getParentId())
                .orElseThrow(() ->  new UserNotFoundException("Parent not found"));
        Student updated = studentMapper.update(request, student);
        updated.setParent(parent);
        return studentMapper.toDto(studentRepository.save(updated));
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        Student student = studentRepository.findById(id).orElseThrow(StudentNotFoundException::new);
        studentRepository.delete(student);
    }


}
