package com.nxhu.ecommercebazar.modules.setting.controller;

import com.nxhu.ecommercebazar.modules.setting.dto.req.UpdateSettingsRequest;
import com.nxhu.ecommercebazar.modules.setting.dto.res.SettingsResponse;
import com.nxhu.ecommercebazar.modules.setting.service.SettingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/settings")
@RequiredArgsConstructor
public class SettingController {

    private final SettingService settingService;

    @GetMapping
    public ResponseEntity<SettingsResponse> getSettings() {
        SettingsResponse settings = settingService.getSettings();
        if (settings == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(settings);
    }

    @PutMapping
    public ResponseEntity<SettingsResponse> update(@Valid @RequestBody UpdateSettingsRequest request) {
        return ResponseEntity.ok(settingService.update(request));
    }
}
