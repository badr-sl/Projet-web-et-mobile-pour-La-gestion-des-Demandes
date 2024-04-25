package org.example.springpfa.Repository;

import org.example.springpfa.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole , Long> {
}
