package com.nxhu.ecommercebazar.modules.permission.controller;

import com.nxhu.ecommercebazar.modules.permission.dto.req.AssignPermissionRequest;
import com.nxhu.ecommercebazar.modules.permission.dto.req.CreatePermissionRequest;
import com.nxhu.ecommercebazar.modules.permission.dto.res.PermissionResponse;
import com.nxhu.ecommercebazar.modules.permission.service.PermissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {

    private final PermissionService permissionService;

    @GetMapping
    public ResponseEntity<List<PermissionResponse>> findAll() {
        return ResponseEntity.ok(permissionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PermissionResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(permissionService.findById(id));
    }

    @PostMapping
    public ResponseEntity<PermissionResponse> create(@Valid @RequestBody CreatePermissionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(permissionService.create(request));
    }

    @PutMapping("/{id}/roles")
    public ResponseEntity<Void> assignRoles(@PathVariable UUID id, @Valid @RequestBody AssignPermissionRequest request) {
        permissionService.assignRoles(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        permissionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
