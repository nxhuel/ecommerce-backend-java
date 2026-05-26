package com.nxhu.ecommercebazar.modules.permission.service;

import com.nxhu.ecommercebazar.modules.permission.dto.req.AssignPermissionRequest;
import com.nxhu.ecommercebazar.modules.permission.dto.req.CreatePermissionRequest;
import com.nxhu.ecommercebazar.modules.permission.dto.res.PermissionResponse;

import java.util.List;
import java.util.UUID;

public interface PermissionService {
    List<PermissionResponse> findAll();
    PermissionResponse findById(UUID id);
    PermissionResponse create(CreatePermissionRequest request);
    void assignRoles(UUID id, AssignPermissionRequest request);
    void delete(UUID id);
}
