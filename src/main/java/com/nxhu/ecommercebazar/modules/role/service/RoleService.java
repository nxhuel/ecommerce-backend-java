package com.nxhu.ecommercebazar.modules.role.service;

import com.nxhu.ecommercebazar.modules.role.dto.req.CreateRoleRequest;
import com.nxhu.ecommercebazar.modules.role.dto.req.UpdateRoleRequest;
import com.nxhu.ecommercebazar.modules.role.dto.res.RoleResponse;

import java.util.List;
import java.util.UUID;

public interface RoleService {
    List<RoleResponse> findAll();
    RoleResponse findById(UUID id);
    RoleResponse create(CreateRoleRequest request);
    RoleResponse update(UUID id, UpdateRoleRequest request);
    void delete(UUID id);
}
