package com.nxhu.ecommercebazar.modules.product.service.impl;

import com.nxhu.ecommercebazar.modules.category.persistence.entity.Category;
import com.nxhu.ecommercebazar.modules.category.persistence.repository.CategoryRepository;
import com.nxhu.ecommercebazar.modules.discount.persistence.entity.Discount;
import com.nxhu.ecommercebazar.modules.discount.persistence.repository.DiscountRepository;
import com.nxhu.ecommercebazar.modules.product.dto.req.CreateProductRequest;
import com.nxhu.ecommercebazar.modules.product.dto.req.UpdateProductRequest;
import com.nxhu.ecommercebazar.modules.product.dto.res.ProductResponse;
import com.nxhu.ecommercebazar.modules.product.persistence.entity.Product;
import com.nxhu.ecommercebazar.modules.product.persistence.entity.ProductImage;
import com.nxhu.ecommercebazar.modules.product.persistence.entity.ProductTag;
import com.nxhu.ecommercebazar.modules.product.persistence.repository.ProductImageRepository;
import com.nxhu.ecommercebazar.modules.product.persistence.repository.ProductRepository;
import com.nxhu.ecommercebazar.modules.product.persistence.repository.ProductTagRepository;
import com.nxhu.ecommercebazar.modules.product.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final DiscountRepository discountRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductTagRepository productTagRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findById(UUID id) {
        return toResponse(findProduct(id));
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse findBySlug(String slug) {
        return productRepository.findBySlug(slug)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + slug));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByCategoryId(UUID categoryId) {
        return productRepository.findByCategoryId(categoryId).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findFeatured() {
        return productRepository.findByFeaturedTrue().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> searchByName(String name) {
        return productRepository.findByNameContainingIgnoreCase(name).stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse create(CreateProductRequest request) {
        if (productRepository.existsBySlug(request.slug())) {
            throw new IllegalArgumentException("Slug already in use: " + request.slug());
        }
        if (productRepository.existsBySku(request.sku())) {
            throw new IllegalArgumentException("SKU already in use: " + request.sku());
        }
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + request.categoryId()));
        Discount discount = null;
        if (request.discountId() != null) {
            discount = discountRepository.findById(request.discountId())
                    .orElseThrow(() -> new EntityNotFoundException("Discount not found: " + request.discountId()));
        }
        Product product = Product.builder()
                .name(request.name())
                .slug(request.slug())
                .category(category)
                .price(request.price())
                .discount(discount)
                .stock(request.stock())
                .rating(0.0)
                .reviewCount(0)
                .brand(request.brand())
                .sku(request.sku())
                .description(request.description())
                .featured(request.featured())
                .build();
        product = productRepository.save(product);

        Product finalProduct = product;
        if (request.images() != null) {
            for (int i = 0; i < request.images().size(); i++) {
                ProductImage image = ProductImage.builder()
                        .product(finalProduct)
                        .imageUrl(request.images().get(i))
                        .sortOrder(i)
                        .build();
                productImageRepository.save(image);
            }
        }
        if (request.tags() != null) {
            for (String tag : request.tags()) {
                ProductTag productTag = ProductTag.builder()
                        .product(finalProduct)
                        .tag(tag)
                        .build();
                productTagRepository.save(productTag);
            }
        }
        return toResponse(productRepository.findById(product.getId()).orElseThrow());
    }

    @Override
    @Transactional
    public ProductResponse update(UUID id, UpdateProductRequest request) {
        Product product = findProduct(id);
        if (request.name() != null) product.setName(request.name());
        if (request.slug() != null) {
            if (!request.slug().equals(product.getSlug()) && productRepository.existsBySlug(request.slug())) {
                throw new IllegalArgumentException("Slug already in use: " + request.slug());
            }
            product.setSlug(request.slug());
        }
        if (request.categoryId() != null) {
            Category category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found: " + request.categoryId()));
            product.setCategory(category);
        }
        if (request.price() != null) product.setPrice(request.price());
        if (request.discountId() != null) {
            Discount discount = discountRepository.findById(request.discountId())
                    .orElseThrow(() -> new EntityNotFoundException("Discount not found: " + request.discountId()));
            product.setDiscount(discount);
        }
        if (request.stock() != null) product.setStock(request.stock());
        if (request.brand() != null) product.setBrand(request.brand());
        if (request.sku() != null) {
            if (!request.sku().equals(product.getSku()) && productRepository.existsBySku(request.sku())) {
                throw new IllegalArgumentException("SKU already in use: " + request.sku());
            }
            product.setSku(request.sku());
        }
        if (request.description() != null) product.setDescription(request.description());
        if (request.featured() != null) product.setFeatured(request.featured());

        productRepository.save(product);

        if (request.images() != null) {
            productImageRepository.deleteByProductId(id);
            for (int i = 0; i < request.images().size(); i++) {
                ProductImage image = ProductImage.builder()
                        .product(product)
                        .imageUrl(request.images().get(i))
                        .sortOrder(i)
                        .build();
                productImageRepository.save(image);
            }
        }
        if (request.tags() != null) {
            productTagRepository.deleteByProductId(id);
            for (String tag : request.tags()) {
                ProductTag productTag = ProductTag.builder()
                        .product(product)
                        .tag(tag)
                        .build();
                productTagRepository.save(productTag);
            }
        }
        return toResponse(productRepository.findById(id).orElseThrow());
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Product product = findProduct(id);
        productRepository.delete(product);
    }

    private Product findProduct(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found: " + id));
    }

    private ProductResponse toResponse(Product product) {
        List<String> images = product.getImages().stream()
                .sorted((a, b) -> {
                    if (a.getSortOrder() == null) return 1;
                    if (b.getSortOrder() == null) return -1;
                    return a.getSortOrder().compareTo(b.getSortOrder());
                })
                .map(ProductImage::getImageUrl)
                .toList();
        List<String> tags = product.getTags().stream()
                .map(ProductTag::getTag)
                .toList();
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getSlug(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getPrice(),
                product.getDiscount() != null ? product.getDiscount().getId() : null,
                product.getDiscount() != null ? product.getDiscount().getCode() : null,
                product.getStock(),
                product.getRating(),
                product.getReviewCount(),
                product.getBrand(),
                product.getSku(),
                product.getDescription(),
                product.getFeatured(),
                images,
                tags,
                product.getCreatedAt(),
                product.getUpdatedAt()
        );
    }
}
