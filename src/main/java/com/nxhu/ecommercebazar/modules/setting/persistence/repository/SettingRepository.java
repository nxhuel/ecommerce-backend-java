package com.nxhu.ecommercebazar.modules.setting.persistence.repository;

import com.nxhu.ecommercebazar.modules.setting.persistence.entity.Setting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SettingRepository extends JpaRepository<Setting, UUID> {
    Optional<Setting> findFirstByOrderByCreatedAtDesc();
}
