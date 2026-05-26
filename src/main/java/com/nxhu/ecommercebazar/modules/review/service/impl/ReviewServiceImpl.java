package com.nxhu.ecommercebazar.modules.review.service.impl;

import com.nxhu.ecommercebazar.modules.product.persistence.entity.Product;
import com.nxhu.ecommercebazar.modules.product.persistence.repository.ProductRepository;
import com.nxhu.ecommercebazar.modules.review.dto.req.CreateReviewRequest;
import com.nxhu.ecommercebazar.modules.review.dto.res.ReviewResponse;
import com.nxhu.ecommercebazar.modules.review.persistence.entity.Review;
import com.nxhu.ecommercebazar.modules.review.persistence.repository.ReviewRepository;
import com.nxhu.ecommercebazar.modules.review.service.ReviewService;
import com.nxhu.ecommercebazar.modules.user.persistence.entity.User;
import com.nxhu.ecommercebazar.modules.user.persistence.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> findByProductId(UUID productId) {
        return reviewRepository.findByProductIdOrderByCreatedAtDesc(productId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResponse> findByUserId(UUID userId) {
        return reviewRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ReviewResponse create(CreateReviewRequest request) {
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + request.productId()));
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + request.userId()));
        Review review = Review.builder()
                .product(product)
                .user(user)
                .rating(request.rating())
                .comment(request.comment())
                .build();
        review = reviewRepository.save(review);

        List<Review> allReviews = reviewRepository.findByProductIdOrderByCreatedAtDesc(product.getId());
        double avgRating = allReviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
        product.setRating(avgRating);
        product.setReviewCount(allReviews.size());
        productRepository.save(product);

        return toResponse(review);
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Review not found: " + id));
        Product product = review.getProduct();
        reviewRepository.delete(review);

        List<Review> remaining = reviewRepository.findByProductIdOrderByCreatedAtDesc(product.getId());
        double avgRating = remaining.stream().mapToInt(Review::getRating).average().orElse(0.0);
        product.setRating(avgRating);
        product.setReviewCount(remaining.size());
        productRepository.save(product);
    }

    private ReviewResponse toResponse(Review review) {
        return new ReviewResponse(
                review.getId(),
                review.getProduct().getId(),
                review.getProduct().getName(),
                review.getUser().getId(),
                review.getUser().getName(),
                review.getRating(),
                review.getComment(),
                review.getCreatedAt(),
                review.getUpdatedAt()
        );
    }
}
