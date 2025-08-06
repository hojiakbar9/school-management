package org.marburgermoschee.schoolmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "parents")
public class Parent {
    @Id
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @OneToMany(mappedBy = "parent")
    private Set<Student> students = new LinkedHashSet<>();

}