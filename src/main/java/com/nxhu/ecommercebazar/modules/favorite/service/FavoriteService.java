package com.nxhu.ecommercebazar.modules.favorite.service;

import com.nxhu.ecommercebazar.modules.favorite.dto.req.ToggleFavoriteRequest;
import com.nxhu.ecommercebazar.modules.favorite.dto.res.FavoriteResponse;

import java.util.List;
import java.util.UUID;

public interface FavoriteService {
    List<FavoriteResponse> findByUserId(UUID userId);
    FavoriteResponse toggle(ToggleFavoriteRequest request);
}
