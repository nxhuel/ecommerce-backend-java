package com.nxhu.ecommercebazar.modules.product.persistence.repository;

import com.nxhu.ecommercebazar.modules.product.persistence.entity.ProductTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductTagRepository extends JpaRepository<ProductTag, UUID> {
    List<ProductTag> findByProductId(UUID productId);
    void deleteByProductId(UUID productId);
}
