package org.marburgermoschee.schoolmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "attendances")
public class Attendance {
    @EmbeddedId
    private AttendanceId id;

    @MapsId("lessonId")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "lesson_id")
    private Lesson lesson;

    @MapsId("studentId")
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;

}