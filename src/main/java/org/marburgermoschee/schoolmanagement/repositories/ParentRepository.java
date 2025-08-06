package org.marburgermoschee.schoolmanagement.repositories;

import org.marburgermoschee.schoolmanagement.entities.Parent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentRepository extends JpaRepository<Parent, Integer> {
}