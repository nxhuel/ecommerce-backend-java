package com.nxhu.ecommercebazar.modules.category.service.impl;

import com.nxhu.ecommercebazar.modules.category.dto.req.CreateCategoryRequest;
import com.nxhu.ecommercebazar.modules.category.dto.req.UpdateCategoryRequest;
import com.nxhu.ecommercebazar.modules.category.dto.res.CategoryResponse;
import com.nxhu.ecommercebazar.modules.category.persistence.entity.Category;
import com.nxhu.ecommercebazar.modules.category.persistence.repository.CategoryRepository;
import com.nxhu.ecommercebazar.modules.category.service.CategoryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findById(UUID id) {
        return toResponse(findCategory(id));
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse findBySlug(String slug) {
        return categoryRepository.findBySlug(slug)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + slug));
    }

    @Override
    @Transactional
    public CategoryResponse create(CreateCategoryRequest request) {
        if (categoryRepository.existsBySlug(request.slug())) {
            throw new IllegalArgumentException("Slug already in use: " + request.slug());
        }
        Category category = Category.builder()
                .name(request.name())
                .slug(request.slug())
                .icon(request.icon())
                .description(request.description())
                .build();
        return toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public CategoryResponse update(UUID id, UpdateCategoryRequest request) {
        Category category = findCategory(id);
        if (request.name() != null) category.setName(request.name());
        if (request.slug() != null) {
            if (!request.slug().equals(category.getSlug()) && categoryRepository.existsBySlug(request.slug())) {
                throw new IllegalArgumentException("Slug already in use: " + request.slug());
            }
            category.setSlug(request.slug());
        }
        if (request.icon() != null) category.setIcon(request.icon());
        if (request.description() != null) category.setDescription(request.description());
        return toResponse(categoryRepository.save(category));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Category category = findCategory(id);
        categoryRepository.delete(category);
    }

    private Category findCategory(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found: " + id));
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getSlug(),
                category.getIcon(),
                category.getDescription(),
                category.getCreatedAt(),
                category.getUpdatedAt()
        );
    }
}
