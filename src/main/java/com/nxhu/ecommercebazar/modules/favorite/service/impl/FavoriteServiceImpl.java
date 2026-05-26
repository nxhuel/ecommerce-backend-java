package com.nxhu.ecommercebazar.modules.favorite.service.impl;

import com.nxhu.ecommercebazar.modules.favorite.dto.req.ToggleFavoriteRequest;
import com.nxhu.ecommercebazar.modules.favorite.dto.res.FavoriteResponse;
import com.nxhu.ecommercebazar.modules.favorite.persistence.entity.Favorite;
import com.nxhu.ecommercebazar.modules.favorite.persistence.repository.FavoriteRepository;
import com.nxhu.ecommercebazar.modules.favorite.service.FavoriteService;
import com.nxhu.ecommercebazar.modules.product.persistence.entity.Product;
import com.nxhu.ecommercebazar.modules.product.persistence.repository.ProductImageRepository;
import com.nxhu.ecommercebazar.modules.product.persistence.repository.ProductRepository;
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
public class FavoriteServiceImpl implements FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;

    @Override
    @Transactional(readOnly = true)
    public List<FavoriteResponse> findByUserId(UUID userId) {
        return favoriteRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public FavoriteResponse toggle(ToggleFavoriteRequest request) {
        var existing = favoriteRepository.findByUserIdAndProductId(request.userId(), request.productId());
        if (existing.isPresent()) {
            favoriteRepository.delete(existing.get());
            return null;
        }
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + request.userId()));
        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + request.productId()));
        Favorite favorite = Favorite.builder()
                .user(user)
                .product(product)
                .build();
        return toResponse(favoriteRepository.save(favorite));
    }

    private FavoriteResponse toResponse(Favorite favorite) {
        String image = productImageRepository.findByProductIdOrderBySortOrder(favorite.getProduct().getId())
                .stream().findFirst().map(img -> img.getImageUrl()).orElse(null);
        return new FavoriteResponse(
                favorite.getId(),
                favorite.getUser().getId(),
                favorite.getProduct().getId(),
                favorite.getProduct().getName(),
                favorite.getProduct().getPrice(),
                image,
                favorite.getCreatedAt()
        );
    }
}
