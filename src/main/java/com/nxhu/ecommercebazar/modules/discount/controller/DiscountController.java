package com.nxhu.ecommercebazar.modules.discount.controller;

import com.nxhu.ecommercebazar.modules.discount.dto.req.CreateDiscountRequest;
import com.nxhu.ecommercebazar.modules.discount.dto.req.UpdateDiscountRequest;
import com.nxhu.ecommercebazar.modules.discount.dto.res.DiscountResponse;
import com.nxhu.ecommercebazar.modules.discount.service.DiscountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/discounts")
@RequiredArgsConstructor
public class DiscountController {

    private final DiscountService discountService;

    @GetMapping
    public ResponseEntity<List<DiscountResponse>> findAll() {
        return ResponseEntity.ok(discountService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiscountResponse> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(discountService.findById(id));
    }

    @GetMapping("/code/{code}")
    public ResponseEntity<DiscountResponse> findByCode(@PathVariable String code) {
        return ResponseEntity.ok(discountService.findByCode(code));
    }

    @PostMapping
    public ResponseEntity<DiscountResponse> create(@Valid @RequestBody CreateDiscountRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(discountService.create(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DiscountResponse> update(@PathVariable UUID id, @Valid @RequestBody UpdateDiscountRequest request) {
        return ResponseEntity.ok(discountService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        discountService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
