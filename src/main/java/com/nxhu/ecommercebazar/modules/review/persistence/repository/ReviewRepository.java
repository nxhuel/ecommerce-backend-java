package com.nxhu.ecommercebazar.modules.review.persistence.repository;

import com.nxhu.ecommercebazar.modules.review.persistence.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    List<Review> findByProductIdOrderByCreatedAtDesc(UUID productId);
    List<Review> findByUserIdOrderByCreatedAtDesc(UUID userId);
}
