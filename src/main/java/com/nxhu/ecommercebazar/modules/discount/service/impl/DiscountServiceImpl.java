package com.nxhu.ecommercebazar.modules.discount.service.impl;

import com.nxhu.ecommercebazar.modules.discount.dto.req.CreateDiscountRequest;
import com.nxhu.ecommercebazar.modules.discount.dto.req.UpdateDiscountRequest;
import com.nxhu.ecommercebazar.modules.discount.dto.res.DiscountResponse;
import com.nxhu.ecommercebazar.modules.discount.persistence.entity.Discount;
import com.nxhu.ecommercebazar.modules.discount.persistence.repository.DiscountRepository;
import com.nxhu.ecommercebazar.modules.discount.service.DiscountService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;

    @Override
    @Transactional(readOnly = true)
    public List<DiscountResponse> findAll() {
        return discountRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public DiscountResponse findById(UUID id) {
        return toResponse(findDiscount(id));
    }

    @Override
    @Transactional(readOnly = true)
    public DiscountResponse findByCode(String code) {
        return discountRepository.findByCode(code)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("Discount not found: " + code));
    }

    @Override
    @Transactional
    public DiscountResponse create(CreateDiscountRequest request) {
        if (discountRepository.existsByCode(request.code())) {
            throw new IllegalArgumentException("Code already in use: " + request.code());
        }
        Discount discount = Discount.builder()
                .code(request.code())
                .percentage(request.percentage())
                .active(request.active())
                .validUntil(request.validUntil())
                .description(request.description())
                .build();
        return toResponse(discountRepository.save(discount));
    }

    @Override
    @Transactional
    public DiscountResponse update(UUID id, UpdateDiscountRequest request) {
        Discount discount = findDiscount(id);
        if (request.code() != null) {
            if (!request.code().equals(discount.getCode()) && discountRepository.existsByCode(request.code())) {
                throw new IllegalArgumentException("Code already in use: " + request.code());
            }
            discount.setCode(request.code());
        }
        if (request.percentage() != null) discount.setPercentage(request.percentage());
        if (request.active() != null) discount.setActive(request.active());
        if (request.validUntil() != null) discount.setValidUntil(request.validUntil());
        if (request.description() != null) discount.setDescription(request.description());
        return toResponse(discountRepository.save(discount));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        Discount discount = findDiscount(id);
        discountRepository.delete(discount);
    }

    private Discount findDiscount(UUID id) {
        return discountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Discount not found: " + id));
    }

    private DiscountResponse toResponse(Discount discount) {
        return new DiscountResponse(
                discount.getId(),
                discount.getCode(),
                discount.getPercentage(),
                discount.getActive(),
                discount.getValidUntil(),
                discount.getDescription(),
                discount.getCreatedAt(),
                discount.getUpdatedAt()
        );
    }
}
