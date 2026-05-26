package com.nxhu.ecommercebazar.modules.setting.service;

import com.nxhu.ecommercebazar.modules.setting.dto.req.UpdateSettingsRequest;
import com.nxhu.ecommercebazar.modules.setting.dto.res.SettingsResponse;

public interface SettingService {
    SettingsResponse getSettings();
    SettingsResponse update(UpdateSettingsRequest request);
}
