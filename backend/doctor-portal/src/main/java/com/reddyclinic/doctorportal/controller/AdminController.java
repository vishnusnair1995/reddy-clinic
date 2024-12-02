package com.reddyclinic.doctorportal.controller;

import com.reddyclinic.doctorportal.dto.*;
import com.reddyclinic.doctorportal.service.AuthService;
import com.reddyclinic.doctorportal.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserService userService;



    @PreAuthorize("hasRole('ADMIN')") // Only allow users with ADMIN role
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully.");
    }


}
