package org.marburgermoschee.schoolmanagement.services;

import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.*;
import org.marburgermoschee.schoolmanagement.entities.*;
import org.marburgermoschee.schoolmanagement.entities.Class;
import org.marburgermoschee.schoolmanagement.exceptions.EntityNotFoundException;
import org.marburgermoschee.schoolmanagement.exceptions.StateConflictException;
import org.marburgermoschee.schoolmanagement.mappers.*;
import org.marburgermoschee.schoolmanagement.repositories.ClassRepository;
import org.marburgermoschee.schoolmanagement.repositories.ParentRepository;
import org.marburgermoschee.schoolmanagement.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class StudentService {
    private final StudentMapper studentMapper;
    private final StudentRepository studentRepository;
    private final ParentRepository parentRepository;
    private final PaymentMapper paymentMapper;
    private final ClassRepository classRepository;
    private final AttendanceMapper attendanceMapper;
    private final ClassMapper classMapper;

    public StudentDto register(RegisterStudentRequest request){
        Student student = studentMapper.toEntity(request);
        Parent parent = parentRepository.getParent(request.getParentId())
                .orElseThrow(() -> new EntityNotFoundException("Parent not found"));
        student.setParent(parent);
        studentRepository.save(student);
        return studentMapper.toDto(student);
    }
    public void recordNewPayment(Integer id, RecordPaymentRequest request){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        student.addPayment(request.getAmount(), request.getPaymentDate());
        studentRepository.save(student);
    }

    public void enroll(Integer id, Integer classId){
        Student student = studentRepository.getStudentWithEnrolledClasses(id).
                orElseThrow(() -> new EntityNotFoundException("Student not found"));
        Class cl = classRepository.findById(classId).orElseThrow(() -> new EntityNotFoundException("Class not found"));
        student.enroll(cl);
        studentRepository.save(student);
    }
    public List<StudentDto> getAllStudents(){
        List<Student> students = studentRepository.getAllWithParents();
        return students.stream().map(studentMapper::toDto).toList();
    }
    public StudentDto getStudent(Integer id){
        Student student = studentRepository
                .getStudentWithParent(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        return studentMapper.toDto(student);
    }

    public List<AttendanceDto> getAllAttendances(Integer id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        Set<Attendance> attendances = student.getAttendances();
        return attendances.stream().map(attendanceMapper::toDto).toList();
    }
    public StudentDto updateStudent(Integer id, RegisterStudentRequest request){
        Student student = studentRepository.getStudentWithParent(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        var parent =  parentRepository.findById(request.getParentId())
                .orElseThrow(() -> new EntityNotFoundException("Parent not found"));
        Student updated = studentMapper.update(request, student);
        updated.setParent(parent);
        return studentMapper.toDto(studentRepository.save(updated));
    }
    public List<PaymentDto> getAllPayments(Integer id){
            Student student = studentRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Student not found"));
            Set<Payment> payments = student.getPayments();
            return payments.stream().map(paymentMapper::toDto).toList();
    }

    public List<ClassDto> getEnrolledClasses(Integer id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        Set<Class> classes = student.getClasses();
        return classes.stream().map(classMapper::toDto).toList();
    }

    public void deleteStudent(Integer id){
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));
        studentRepository.delete(student);
    }
    public void unenroll(Integer id, Integer classId){
        Student student = studentRepository.getStudentWithEnrolledClasses(id).
                orElseThrow(() -> new EntityNotFoundException("Student not found"));
        Class cl = classRepository.findById(classId).orElseThrow(() -> new EntityNotFoundException("Class not found"));
        if(!student.getClasses().contains(cl))
            throw new StateConflictException("Student is not enrolled in the class");
        student.getClasses().remove(cl);
        studentRepository.save(student);
    }
}
