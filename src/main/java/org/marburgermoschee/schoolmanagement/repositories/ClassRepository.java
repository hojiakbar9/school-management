package org.marburgermoschee.schoolmanagement.repositories;

import org.marburgermoschee.schoolmanagement.entities.Class;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClassRepository extends JpaRepository<Class, Integer> {
    @EntityGraph(attributePaths ={"teachers", "teachers.user"})
    @Query("SELECT c FROM Class c WHERE c.id = :id")
    Optional<Class> getClassWithTeachers(@Param("id") Integer id);

    @EntityGraph(attributePaths ={"teachers", "teachers.user"})
    @Query("SELECT c FROM Class c")
    List<Class> getAllWithTeachers();

    @EntityGraph(attributePaths = {"students"})
    @Query("SELECT c FROM Class c WHERE c.id = :id")
    Optional<Class> getClassWithStudents(@Param("id") Integer id);
}