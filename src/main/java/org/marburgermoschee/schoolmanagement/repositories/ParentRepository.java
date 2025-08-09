package org.marburgermoschee.schoolmanagement.repositories;

import org.marburgermoschee.schoolmanagement.entities.Parent;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ParentRepository extends JpaRepository<Parent, Integer> {
    @EntityGraph(attributePaths = "user")
    @Query("SELECT p FROM Parent p ")
    List<Parent> getAll();

    @EntityGraph(attributePaths = "user")
    @Query("SELECT p FROM Parent p WHERE p.id =:id")
    Optional<Parent> getParent(@Param("id") Integer id);
}