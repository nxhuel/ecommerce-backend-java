package com.nxhu.ecommercebazar.modules.permission.persistence.repository;

import com.nxhu.ecommercebazar.modules.permission.persistence.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PermissionRepository extends JpaRepository<Permission, UUID> {
    Optional<Permission> findByKey(String key);
}
