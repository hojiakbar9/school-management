package org.marburgermoschee.schoolmanagement.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.marburgermoschee.schoolmanagement.exceptions.StateConflictException;

import java.math.BigDecimal;
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
    @JoinColumn(name = "parent_id")
    private Parent parent;


    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "student")
    @ToString.Exclude
    private Set<Attendance> attendances = new LinkedHashSet<>();

    @OneToMany(mappedBy = "student", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @ToString.Exclude
    private Set<Payment> payments = new LinkedHashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
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
    public void addPayment(BigDecimal amount, LocalDate date){
        Payment payment = new Payment();
        payment.setStudent(this);
        payment.setAmount(amount);
        payment.setPaymentDate(date);
        payments.add(payment);
    }
    public void enroll(Class cl) {
        if(classes.contains(cl))
            throw new StateConflictException("Student already enrolled");
        classes.add(cl);
    }
}