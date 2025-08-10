package org.marburgermoschee.schoolmanagement.controllers;

import org.marburgermoschee.schoolmanagement.entities.Teacher;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {
  @EntityGraph(attributePaths = "user")
  @Query("SELECT t FROM Teacher t ")
  List<Teacher> getAll();

  @EntityGraph(attributePaths = "user")
  @Query("SELECT t FROM Teacher t WHERE t.id=:id")
  Optional<Teacher> getTeacher(@Param("id") Integer id);
}