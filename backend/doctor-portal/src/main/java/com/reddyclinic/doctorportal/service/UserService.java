package com.reddyclinic.doctorportal.service;

import com.reddyclinic.doctorportal.dto.*;
import com.reddyclinic.doctorportal.entity.Role;
import com.reddyclinic.doctorportal.entity.User;
import com.reddyclinic.doctorportal.exception.ResourceNotFoundException;
import com.reddyclinic.doctorportal.mapper.UserMapper;
import com.reddyclinic.doctorportal.repository.RoleRepository;
import com.reddyclinic.doctorportal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

import static com.reddyclinic.doctorportal.util.MessageConstants.USER_SAVE_SUCCESS;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponse registerUser(UserCreateRequest request) {
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt password
        Role userRole = roleRepository.findByRoleName("USER")
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(userRole));
        User savedUser = userRepository.save(user);
        return new UserResponse(savedUser.getId(),USER_SAVE_SUCCESS);
    }

    public UserDTO getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toDTO(user);
    }
    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

}
