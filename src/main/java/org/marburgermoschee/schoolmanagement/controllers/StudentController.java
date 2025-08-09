package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.*;
import org.marburgermoschee.schoolmanagement.services.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public ResponseEntity<StudentDto> register(
            @Valid @RequestBody RegisterStudentRequest request,
            UriComponentsBuilder builder) {
       StudentDto studentdto =  studentService.register(request);
       URI uri = builder.path("/students/{studentId}").buildAndExpand(studentdto.getId()).toUri();
       return ResponseEntity.created(uri).body(studentdto);
    }
    @PostMapping("/{id}/payments")
    public void recordNewPayment(
            @PathVariable Integer id,
            @Valid @RequestBody RecordPaymentRequest request) {
        studentService.recordNewPayment(id, request);
    }
    @PostMapping("/{id}/classes/{classId}")
    public void enroll(@PathVariable Integer id, @PathVariable Integer classId) {
        studentService.enroll(id, classId);
    }

    @GetMapping
    public List<StudentDto>getAllStudents(){
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentDto getStudent(@PathVariable Integer id){
        return studentService.getStudent(id);
    }

    @GetMapping("/{id}/attendances")
    public List<AttendanceDto> getAttendances(@PathVariable Integer id){
        return studentService.getAllAttendances(id);
    }
    @GetMapping("{id}/payments")
    public List<PaymentDto> getPayments(@PathVariable Integer id){
       return studentService.getAllPayments(id);
    }

    @GetMapping("/{id}/classes")
    public List<ClassDto> getEnrolledClasses(@PathVariable Integer id){
       return studentService.getEnrolledClasses(id);
    }
    @PutMapping("/{id}")
    public StudentDto updateStudent(
            @Valid @RequestBody RegisterStudentRequest request,
            @PathVariable Integer id) {
        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Integer id) {
        studentService.deleteStudent(id);
    }
    @DeleteMapping("/{id}/classes/{classId}")
    public void unenroll(@PathVariable Integer id, @PathVariable Integer classId) {
        studentService.unenroll(id, classId);
    }


}
