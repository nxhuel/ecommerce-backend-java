package com.nxhu.ecommercebazar.modules.audit.service;

import com.nxhu.ecommercebazar.modules.audit.dto.res.AuditLogResponse;

import java.util.List;
import java.util.UUID;

public interface AuditLogService {
    List<AuditLogResponse> findAll();
    List<AuditLogResponse> findByUserId(UUID userId);
}
