package org.cristianvelasquezp.recipes.repositories;

import org.cristianvelasquezp.recipes.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByEmail(String username);
}
