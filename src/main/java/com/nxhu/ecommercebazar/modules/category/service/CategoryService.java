package com.nxhu.ecommercebazar.modules.category.service;

import com.nxhu.ecommercebazar.modules.category.dto.req.CreateCategoryRequest;
import com.nxhu.ecommercebazar.modules.category.dto.req.UpdateCategoryRequest;
import com.nxhu.ecommercebazar.modules.category.dto.res.CategoryResponse;

import java.util.List;
import java.util.UUID;

public interface CategoryService {
    List<CategoryResponse> findAll();
    CategoryResponse findById(UUID id);
    CategoryResponse findBySlug(String slug);
    CategoryResponse create(CreateCategoryRequest request);
    CategoryResponse update(UUID id, UpdateCategoryRequest request);
    void delete(UUID id);
}
