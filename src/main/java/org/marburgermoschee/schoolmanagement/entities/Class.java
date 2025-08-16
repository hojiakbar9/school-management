package org.marburgermoschee.schoolmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.marburgermoschee.schoolmanagement.exceptions.DuplicateEntryException;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "classes")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private ClassType type;

    @Column(name = "level")
    private String level;

    @OneToMany(mappedBy = "classField")
    private Set<Lesson> lessons = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "students_classes",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id"))
    private Set<Student> students = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "teachers_classes",
            joinColumns = @JoinColumn(name = "class_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id"))
    private Set<Teacher> teachers = new LinkedHashSet<>();

    public void addTeacher(Teacher teacher) {
        if(teachers.contains(teacher))
            throw new DuplicateEntryException("Teacher already assigned to the class");
        teachers.add(teacher);
    }
}