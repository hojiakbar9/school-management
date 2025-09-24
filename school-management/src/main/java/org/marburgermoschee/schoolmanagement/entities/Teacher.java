package org.marburgermoschee.schoolmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "teachers")
public class Teacher {
    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @ManyToMany(mappedBy = "teachers")
    private Set<Class> classes = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "teachers")
    private Set<Lesson> lessons = new LinkedHashSet<>();

}