package com.conference.demo.repository;

import com.conference.demo.entities.Role;
import com.conference.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    int deleteUserById(long id);

    List<User> findAllByRole(Role role);
}
