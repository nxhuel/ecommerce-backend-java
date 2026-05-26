package com.nxhu.ecommercebazar.modules.permission.service.impl;

import com.nxhu.ecommercebazar.modules.permission.dto.req.AssignPermissionRequest;
import com.nxhu.ecommercebazar.modules.permission.dto.req.CreatePermissionRequest;
import com.nxhu.ecommercebazar.modules.permission.dto.res.PermissionResponse;
import com.nxhu.ecommercebazar.modules.permission.persistence.entity.Permission;
import com.nxhu.ecommercebazar.modules.permission.persistence.repository.PermissionRepository;
import com.nxhu.ecommercebazar.modules.permission.service.PermissionService;
import com.nxhu.ecommercebazar.modules.role.persistence.entity.Role;
import com.nxhu.ecommercebazar.modules.role.persistence.entity.RolePermission;
import com.nxhu.ecommercebazar.modules.role.persistence.repository.RolePermissionRepository;
import com.nxhu.ecommercebazar.modules.role.persistence.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final RolePermissionRepository rolePermissionRepository;

    @Override
    @Transactional(readOnly = true)
    public List<PermissionResponse> findAll() {
        return permissionRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionResponse findById(UUID id) {
        return toResponse(findPermission(id));
    }

    @Override
    @Transactional
    public PermissionResponse create(CreatePermissionRequest request) {
        Permission permission = Permission.builder()
                .key(request.key())
                .build();
        return toResponse(permissionRepository.save(permission));
    }

    @Override
    @Transactional
    public void assignRoles(UUID id, AssignPermissionRequest request) {
        Permission permission = findPermission(id);
        rolePermissionRepository.deleteByRoleIdAndPermissionId(id, null);

        if (request.roleIds() != null) {
            for (UUID roleId : request.roleIds()) {
                Role role = roleRepository.findById(roleId)
                        .orElseThrow(() -> new EntityNotFoundException("Role not found: " + roleId));
                RolePermission rp = RolePermission.builder()
                        .role(role)
                        .permission(permission)
                        .build();
                rolePermissionRepository.save(rp);
            }
        }
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Permission permission = findPermission(id);
        permissionRepository.delete(permission);
    }

    private Permission findPermission(UUID id) {
        return permissionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Permission not found: " + id));
    }

    private PermissionResponse toResponse(Permission permission) {
        List<String> roleNames = permission.getRolePermissions().stream()
                .map(rp -> rp.getRole().getName().name())
                .toList();
        return new PermissionResponse(
                permission.getId(),
                permission.getKey(),
                roleNames,
                permission.getCreatedAt(),
                permission.getUpdatedAt()
        );
    }
}
