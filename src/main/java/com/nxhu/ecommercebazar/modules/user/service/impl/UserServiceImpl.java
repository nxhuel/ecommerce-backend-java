package com.nxhu.ecommercebazar.modules.user.service.impl;

import com.nxhu.ecommercebazar.modules.role.persistence.entity.Role;
import com.nxhu.ecommercebazar.modules.role.persistence.repository.RoleRepository;
import com.nxhu.ecommercebazar.modules.user.dto.req.CreateUserRequest;
import com.nxhu.ecommercebazar.modules.user.dto.req.UpdateUserRequest;
import com.nxhu.ecommercebazar.modules.user.dto.res.UserResponse;
import com.nxhu.ecommercebazar.modules.user.persistence.entity.User;
import com.nxhu.ecommercebazar.modules.user.persistence.repository.UserRepository;
import com.nxhu.ecommercebazar.modules.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> findAll() {
        return userRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findById(UUID id) {
        return toResponse(findUser(id));
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(this::toResponse)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + email));
    }

    @Override
    @Transactional
    public UserResponse create(CreateUserRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already in use: " + request.email());
        }
        Role role = roleRepository.findByName(Role.RoleName.valueOf(request.roleName().toUpperCase()))
                .orElseThrow(() -> new EntityNotFoundException("Role not found: " + request.roleName()));
        User user = User.builder()
                .name(request.name())
                .email(request.email())
                .password(request.password())
                .role(role)
                .avatar(request.avatar())
                .phone(request.phone())
                .address(request.address())
                .build();
        return toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public UserResponse update(UUID id, UpdateUserRequest request) {
        User user = findUser(id);
        if (request.name() != null) user.setName(request.name());
        if (request.avatar() != null) user.setAvatar(request.avatar());
        if (request.phone() != null) user.setPhone(request.phone());
        if (request.address() != null) user.setAddress(request.address());
        return toResponse(userRepository.save(user));
    }

    @Override
    @Transactional
    public void delete(UUID id) {
        User user = findUser(id);
        userRepository.delete(user);
    }

    private User findUser(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found: " + id));
    }

    private UserResponse toResponse(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().getName().name(),
                user.getAvatar(),
                user.getPhone(),
                user.getAddress(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
