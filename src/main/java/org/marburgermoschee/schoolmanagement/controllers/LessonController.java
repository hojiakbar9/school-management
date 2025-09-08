package org.marburgermoschee.schoolmanagement.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.MarkAttendanceRequest;
import org.marburgermoschee.schoolmanagement.entities.Attendance;
import org.marburgermoschee.schoolmanagement.entities.Lesson;
import org.marburgermoschee.schoolmanagement.entities.Student;
import org.marburgermoschee.schoolmanagement.exceptions.EntityNotFoundException;
import org.marburgermoschee.schoolmanagement.repositories.AttendanceRepository;
import org.marburgermoschee.schoolmanagement.repositories.LessonRepository;
import org.marburgermoschee.schoolmanagement.repositories.StudentRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/lessons")
public class LessonController {
    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;

    @PostMapping("/{id}/attendances")
    public void markAttendance(
            @PathVariable("id") Integer lessonId,
            @Valid @RequestBody MarkAttendanceRequest request){
        Lesson lesson = lessonRepository
                .findById(lessonId).orElseThrow(() -> new EntityNotFoundException("lesson does not exist!"));
        Student student = studentRepository.
                findById(request.getStudentId()).orElseThrow(() -> new EntityNotFoundException("student does not exist"));


        Attendance attendance = new Attendance();
        attendance.setLesson(lesson);
        attendance.setStudent(student);
        attendance.setStatus(request.getStatus());

        attendanceRepository.save(attendance);
    }
}
