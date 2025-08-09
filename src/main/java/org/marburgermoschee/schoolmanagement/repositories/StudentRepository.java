package org.marburgermoschee.schoolmanagement.repositories;

import org.marburgermoschee.schoolmanagement.entities.Student;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Integer> {
  @EntityGraph(attributePaths = "parent.user")
  @Query("SELECT s FROM Student  s")
  List<Student> getAllWithParents();

  @EntityGraph(attributePaths = "parent.user")
  @Query("SELECT s FROM Student  s WHERE s.id=:id")
  Optional<Student> getStudentWithParent(@Param("id") Integer id);

  @EntityGraph(attributePaths = "classes")
  @Query("SELECT s FROM Student  s WHERE s.id=:id")
  Optional<Student> getStudentWithEnrolledClasses(@Param("id") Integer id);
}