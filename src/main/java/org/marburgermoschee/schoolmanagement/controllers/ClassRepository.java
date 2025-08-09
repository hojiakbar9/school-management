package org.marburgermoschee.schoolmanagement.controllers;

import org.marburgermoschee.schoolmanagement.entities.Class;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClassRepository extends JpaRepository<Class, Integer> {
}