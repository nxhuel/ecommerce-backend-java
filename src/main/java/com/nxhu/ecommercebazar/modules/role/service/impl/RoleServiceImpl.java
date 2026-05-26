package com.nxhu.ecommercebazar.modules.role.service.impl;

import com.nxhu.ecommercebazar.modules.role.dto.req.CreateRoleRequest;
import com.nxhu.ecommercebazar.modules.role.dto.req.UpdateRoleRequest;
import com.nxhu.ecommercebazar.modules.role.dto.res.RoleResponse;
import com.nxhu.ecommercebazar.modules.role.persistence.entity.Role;
import com.nxhu.ecommercebazar.modules.role.persistence.repository.RoleRepository;
import com.nxhu.ecommercebazar.modules.role.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<RoleResponse> findAll() {
        return roleRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RoleResponse findById(UUID id) {
        return toResponse(findRole(id));
    }

    @Override
    @Transactional
    public RoleResponse create(CreateRoleRequest request) {
        Role role = Role.builder()
                .name(Role.RoleName.valueOf(request.name().toUpperCase()))
                .label(request.label())
                .build();
        return toResponse(roleRepository.save(role));
    }

    @Override
    @Transactional
    public RoleResponse update(UUID id, UpdateRoleRequest request) {
        Role role = findRole(id);
        if (request.name() != null) {
            role.setName(Role.RoleName.valueOf(request.name().toUpperCase()));
        }
        if (request.label() != null) {
            role.setLabel(request.label());
        }
        return toResponse(roleRepository.save(role));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Role role = findRole(id);
        roleRepository.delete(role);
    }

    private Role findRole(UUID id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + id));
    }

    private RoleResponse toResponse(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getName().name(),
                role.getLabel(),
                role.getCreatedAt(),
                role.getUpdatedAt()
        );
    }
}
