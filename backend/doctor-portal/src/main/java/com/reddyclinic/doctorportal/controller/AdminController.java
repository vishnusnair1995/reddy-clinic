package com.reddyclinic.doctorportal.controller;

import com.reddyclinic.doctorportal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
/*Constructor injection is a best practice for the following reasons:
    1. Immutability:
    Dependencies injected through constructors are typically marked final, ensuring they can't be modified after object creation. This leads to more predictable, stable objects.
    2. Clear Dependencies:
    The constructor makes it explicit which dependencies the class needs. This improves readability and makes it easier to spot missing dependencies.
    3. Testability:
    Constructor injection allows for easier unit testing. Dependencies can be mocked or stubbed when testing the class, whereas field injection requires reflection, making it more complex
 */
    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole(T(com.reddyclinic.doctorportal.util.RoleConstants).ROLE_ADMIN)")// Only allow users with ADMIN role
    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully.");
    }


}
