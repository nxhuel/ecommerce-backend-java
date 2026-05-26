package com.nxhu.ecommercebazar.modules.discount.persistence.repository;

import com.nxhu.ecommercebazar.modules.discount.persistence.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount, UUID> {
    Optional<Discount> findByCode(String code);
    boolean existsByCode(String code);
}
