package com.nxhu.ecommercebazar.modules.review.controller;

import com.nxhu.ecommercebazar.modules.review.dto.req.CreateReviewRequest;
import com.nxhu.ecommercebazar.modules.review.dto.res.ReviewResponse;
import com.nxhu.ecommercebazar.modules.review.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewResponse>> findByProductId(@PathVariable UUID productId) {
        return ResponseEntity.ok(reviewService.findByProductId(productId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewResponse>> findByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(reviewService.findByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<ReviewResponse> create(@Valid @RequestBody CreateReviewRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewService.create(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
