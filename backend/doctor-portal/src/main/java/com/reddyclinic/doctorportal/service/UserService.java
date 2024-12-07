package com.reddyclinic.doctorportal.service;

import com.reddyclinic.doctorportal.dto.*;
import com.reddyclinic.doctorportal.entity.Role;
import com.reddyclinic.doctorportal.entity.User;
import com.reddyclinic.doctorportal.exception.ResourceNotFoundException;
import com.reddyclinic.doctorportal.exception.RoleNotFoundException;
import com.reddyclinic.doctorportal.mapper.UserMapper;
import com.reddyclinic.doctorportal.repository.RoleRepository;
import com.reddyclinic.doctorportal.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.reddyclinic.doctorportal.util.MessageConstants.USER_SAVE_SUCCESS;

@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse registerUser(UserCreateRequest request) {
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        Role userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        user.setRoles(Set.of(userRole));
        User savedUser = userRepository.save(user);
        log.info("Registration success for User {}", user.getName());
        return new UserResponse(savedUser.getId(), USER_SAVE_SUCCESS);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toDTO(user);
    }

    public UserResponse deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
        log.info("Deletion success for User");
        return new UserResponse(id, USER_SAVE_SUCCESS);
    }
}
