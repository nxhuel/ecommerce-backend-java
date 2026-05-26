package com.nxhu.ecommercebazar.modules.audit.service.impl;

import com.nxhu.ecommercebazar.modules.audit.dto.res.AuditLogResponse;
import com.nxhu.ecommercebazar.modules.audit.persistence.repository.AuditLogRepository;
import com.nxhu.ecommercebazar.modules.audit.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogResponse> findAll() {
        return auditLogRepository.findAllByOrderByCreatedAtDesc().stream()
                .map(a -> new AuditLogResponse(
                        a.getId(),
                        a.getUser() != null ? a.getUser().getId() : null,
                        a.getUser() != null ? a.getUser().getName() : "SYSTEM",
                        a.getAction(),
                        a.getEntity(),
                        a.getCreatedAt()
                ))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuditLogResponse> findByUserId(UUID userId) {
        return auditLogRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(a -> new AuditLogResponse(
                        a.getId(),
                        a.getUser().getId(),
                        a.getUser().getName(),
                        a.getAction(),
                        a.getEntity(),
                        a.getCreatedAt()
                ))
                .toList();
    }
}
