package org.marburgermoschee.schoolmanagement.services;

import lombok.AllArgsConstructor;
import org.marburgermoschee.schoolmanagement.dtos.MarkAttendanceRequest;
import org.marburgermoschee.schoolmanagement.entities.Attendance;
import org.marburgermoschee.schoolmanagement.entities.Lesson;
import org.marburgermoschee.schoolmanagement.entities.Student;
import org.marburgermoschee.schoolmanagement.exceptions.EntityNotFoundException;
import org.marburgermoschee.schoolmanagement.repositories.AttendanceRepository;
import org.marburgermoschee.schoolmanagement.repositories.LessonRepository;
import org.marburgermoschee.schoolmanagement.repositories.StudentRepository;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;

    public void markAttendance(Integer lessonId, MarkAttendanceRequest request){
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
