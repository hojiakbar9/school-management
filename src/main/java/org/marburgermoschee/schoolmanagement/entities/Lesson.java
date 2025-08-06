package org.marburgermoschee.schoolmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "lessons")
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "class_id")
    private Class classField;

    @Column(name = "date", insertable = false, updatable = false)
    private LocalDate date;

    @Column(name = "topic")
    private String topic;

    @OneToMany(mappedBy = "lesson")
    private Set<Attendance> attendances = new LinkedHashSet<>();

    @ManyToMany
    private Set<Teacher> teachers = new LinkedHashSet<>();

}