package com.nxhu.ecommercebazar.modules.role.persistence.repository;

import com.nxhu.ecommercebazar.modules.role.persistence.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RolePermissionRepository extends JpaRepository<RolePermission, UUID> {
    void deleteByRoleIdAndPermissionId(UUID roleId, UUID permissionId);
}
