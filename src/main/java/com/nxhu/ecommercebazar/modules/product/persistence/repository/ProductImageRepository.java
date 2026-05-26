package com.nxhu.ecommercebazar.modules.product.persistence.repository;

import com.nxhu.ecommercebazar.modules.product.persistence.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {
    List<ProductImage> findByProductIdOrderBySortOrder(UUID productId);
    void deleteByProductId(UUID productId);
}
