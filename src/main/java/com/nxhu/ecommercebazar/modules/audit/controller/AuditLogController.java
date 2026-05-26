package com.nxhu.ecommercebazar.modules.audit.controller;

import com.nxhu.ecommercebazar.modules.audit.dto.res.AuditLogResponse;
import com.nxhu.ecommercebazar.modules.audit.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/audit-logs")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditLogService;

    @GetMapping
    public ResponseEntity<List<AuditLogResponse>> findAll() {
        return ResponseEntity.ok(auditLogService.findAll());
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AuditLogResponse>> findByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(auditLogService.findByUserId(userId));
    }
}
