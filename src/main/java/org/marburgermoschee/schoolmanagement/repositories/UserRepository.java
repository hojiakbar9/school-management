package org.marburgermoschee.schoolmanagement.repositories;

import org.marburgermoschee.schoolmanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    boolean existsUserByEmail(String email);
}