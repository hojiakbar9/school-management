package org.marburgermoschee.schoolmanagement.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class AttendanceId implements Serializable {
    @Serial
    private static final long serialVersionUID = -4943088627129986726L;
    @Column(name = "lesson_id")
    private Integer lessonId;

    @Column(name = "student_id")
    private Integer studentId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AttendanceId entity = (AttendanceId) o;
        return Objects.equals(this.studentId, entity.studentId) &&
                Objects.equals(this.lessonId, entity.lessonId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, lessonId);
    }

}