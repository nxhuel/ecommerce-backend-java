package com.nxhu.ecommercebazar.modules.discount.service;

import com.nxhu.ecommercebazar.modules.discount.dto.req.CreateDiscountRequest;
import com.nxhu.ecommercebazar.modules.discount.dto.req.UpdateDiscountRequest;
import com.nxhu.ecommercebazar.modules.discount.dto.res.DiscountResponse;

import java.util.List;
import java.util.UUID;

public interface DiscountService {
    List<DiscountResponse> findAll();
    DiscountResponse findById(UUID id);
    DiscountResponse findByCode(String code);
    DiscountResponse create(CreateDiscountRequest request);
    DiscountResponse update(UUID id, UpdateDiscountRequest request);
    void delete(UUID id);
}
