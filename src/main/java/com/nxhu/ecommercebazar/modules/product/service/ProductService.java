package com.nxhu.ecommercebazar.modules.product.service;

import com.nxhu.ecommercebazar.modules.product.dto.req.CreateProductRequest;
import com.nxhu.ecommercebazar.modules.product.dto.req.UpdateProductRequest;
import com.nxhu.ecommercebazar.modules.product.dto.res.ProductResponse;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<ProductResponse> findAll();
    ProductResponse findById(UUID id);
    ProductResponse findBySlug(String slug);
    List<ProductResponse> findByCategoryId(UUID categoryId);
    List<ProductResponse> findFeatured();
    List<ProductResponse> searchByName(String name);
    ProductResponse create(CreateProductRequest request);
    ProductResponse update(UUID id, UpdateProductRequest request);
    void delete(UUID id);
}
