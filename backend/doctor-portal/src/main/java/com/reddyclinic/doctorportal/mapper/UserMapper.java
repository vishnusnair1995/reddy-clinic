package com.reddyclinic.doctorportal.mapper;

import com.reddyclinic.doctorportal.dto.*;
import com.reddyclinic.doctorportal.entity.*;
import com.reddyclinic.doctorportal.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDTO toDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getPhone());
    }

    public User toEntity(UserCreateRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword()); // Password should be hashed in service
        user.setPhone(request.getPhone());
        return user;
    }
}

