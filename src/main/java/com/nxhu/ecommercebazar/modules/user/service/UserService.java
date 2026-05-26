package com.nxhu.ecommercebazar.modules.user.service;

import com.nxhu.ecommercebazar.modules.user.dto.req.CreateUserRequest;
import com.nxhu.ecommercebazar.modules.user.dto.req.UpdateUserRequest;
import com.nxhu.ecommercebazar.modules.user.dto.res.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponse> findAll();
    UserResponse findById(UUID id);
    UserResponse findByEmail(String email);
    UserResponse create(CreateUserRequest request);
    UserResponse update(UUID id, UpdateUserRequest request);
    void delete(UUID id);
}
