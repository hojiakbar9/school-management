package org.marburgermoschee.schoolmanagement.repositories;

import org.marburgermoschee.schoolmanagement.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {

  @Query("SELECT DISTINCT s FROM Student s " +
          "LEFT JOIN FETCH s.attendances a " +
          "LEFT JOIN FETCH a.lesson l " +
          "LEFT JOIN FETCH l.classField " +
          "LEFT JOIN FETCH s.parent p " +
          "LEFT JOIN FETCH p.user " +
          "LEFT JOIN FETCH s.payments " +
          "LEFT JOIN FETCH s.classes")
  List<Student> getAll();

  @Query("SELECT DISTINCT s FROM Student s " +
          "LEFT JOIN FETCH s.attendances a " +
          "LEFT JOIN FETCH a.lesson l " +
          "LEFT JOIN FETCH l.classField " +
          "LEFT JOIN FETCH s.parent p " +
          "LEFT JOIN FETCH p.user " +
          "LEFT JOIN FETCH s.payments " +
          "LEFT JOIN FETCH s.classes " +
          "WHERE s.id =:id"
  )
  Optional<Student> getStudent(Integer id);
}