package com.nxhu.ecommercebazar.modules.review.service;

import com.nxhu.ecommercebazar.modules.review.dto.req.CreateReviewRequest;
import com.nxhu.ecommercebazar.modules.review.dto.res.ReviewResponse;

import java.util.List;
import java.util.UUID;

public interface ReviewService {
    List<ReviewResponse> findByProductId(UUID productId);
    List<ReviewResponse> findByUserId(UUID userId);
    ReviewResponse create(CreateReviewRequest request);
    void delete(UUID id);
}
