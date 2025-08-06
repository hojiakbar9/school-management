package org.marburgermoschee.schoolmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "students")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private GenderType gender;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.SET_NULL)
    @JoinColumn(name = "parent_id")
    private Parent parent;


    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "student")
    @ToString.Exclude
    private Set<Attendance> attendances = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student")
    @ToString.Exclude
    private Set<Payment> payments = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(
            name="students_classes",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name="class_id")
    )
    @ToString.Exclude
    private Set<Class> classes = new LinkedHashSet<>();

    public boolean hasParent(){
        return parent != null;
    }
}