package com.nxhu.ecommercebazar.modules.product.persistence.repository;

import com.nxhu.ecommercebazar.modules.product.persistence.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findBySlug(String slug);
    Optional<Product> findBySku(String sku);
    boolean existsBySlug(String slug);
    boolean existsBySku(String sku);
    List<Product> findByCategoryId(UUID categoryId);
    List<Product> findByFeaturedTrue();
    List<Product> findByNameContainingIgnoreCase(String name);
}
