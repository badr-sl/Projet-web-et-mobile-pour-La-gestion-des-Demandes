package org.example.springpfa.Repository;


import org.example.springpfa.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    public Optional<User> findById(Long id);
    public User findByEmail(String email);
    public User findByUsername(String userName);
}
