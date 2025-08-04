package org.marburgermoschee.schoolmanagement.repositories;

import org.marburgermoschee.schoolmanagement.entities.GenderType;
import org.marburgermoschee.schoolmanagement.entities.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
  @EntityGraph(attributePaths = "parent")
  @Query("SELECT s FROM Student s")
  List<Student> getAll();

  List<Student> findAllByGender(GenderType gender);
}