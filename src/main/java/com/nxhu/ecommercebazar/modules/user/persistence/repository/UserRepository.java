package com.nxhu.ecommercebazar.modules.user.persistence.repository;

import com.nxhu.ecommercebazar.modules.user.persistence.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);
}
