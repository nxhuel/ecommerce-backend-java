package com.nxhu.ecommercebazar.modules.favorite.controller;

import com.nxhu.ecommercebazar.modules.favorite.dto.req.ToggleFavoriteRequest;
import com.nxhu.ecommercebazar.modules.favorite.dto.res.FavoriteResponse;
import com.nxhu.ecommercebazar.modules.favorite.service.FavoriteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<FavoriteResponse>> findByUserId(@PathVariable UUID userId) {
        return ResponseEntity.ok(favoriteService.findByUserId(userId));
    }

    @PostMapping("/toggle")
    public ResponseEntity<FavoriteResponse> toggle(@Valid @RequestBody ToggleFavoriteRequest request) {
        FavoriteResponse result = favoriteService.toggle(request);
        if (result == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(result);
    }
}
