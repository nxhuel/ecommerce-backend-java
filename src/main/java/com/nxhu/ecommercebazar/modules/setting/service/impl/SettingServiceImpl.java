package com.nxhu.ecommercebazar.modules.setting.service.impl;

import com.nxhu.ecommercebazar.modules.setting.dto.req.UpdateSettingsRequest;
import com.nxhu.ecommercebazar.modules.setting.dto.res.SettingsResponse;
import com.nxhu.ecommercebazar.modules.setting.persistence.entity.Setting;
import com.nxhu.ecommercebazar.modules.setting.persistence.repository.SettingRepository;
import com.nxhu.ecommercebazar.modules.setting.service.SettingService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;

    @Override
    @Transactional(readOnly = true)
    public SettingsResponse getSettings() {
        return settingRepository.findFirstByOrderByCreatedAtDesc()
                .map(this::toResponse)
                .orElse(null);
    }

    @Override
    @Transactional
    public SettingsResponse update(UpdateSettingsRequest request) {
        Setting setting = settingRepository.findFirstByOrderByCreatedAtDesc()
                .orElseGet(() -> Setting.builder().build());
        if (request.storeName() != null) setting.setStoreName(request.storeName());
        if (request.currency() != null) setting.setCurrency(request.currency());
        if (request.city() != null) setting.setCity(request.city());
        if (request.country() != null) setting.setCountry(request.country());
        if (request.email() != null) setting.setEmail(request.email());
        if (request.phone() != null) setting.setPhone(request.phone());
        if (request.address() != null) setting.setAddress(request.address());
        return toResponse(settingRepository.save(setting));
    }

    private SettingsResponse toResponse(Setting setting) {
        return new SettingsResponse(
                setting.getId(),
                setting.getStoreName(),
                setting.getCurrency(),
                setting.getCity(),
                setting.getCountry(),
                setting.getEmail(),
                setting.getPhone(),
                setting.getAddress(),
                setting.getCreatedAt(),
                setting.getUpdatedAt()
        );
    }
}
