package com.nxhu.ecommercebazar.modules.discount.persistence.entity;

import com.nxhu.ecommercebazar.modules.product.persistence.entity.Product;
import com.nxhu.ecommercebazar.modules.shared.persistence.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "discounts")
public class Discount extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private Integer percentage;

    @Column(nullable = false)
    private Boolean active;

    @Column(name = "valid_until", nullable = false)
    private LocalDateTime validUntil;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Builder.Default
    @OneToMany(mappedBy = "discount", fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();
}
