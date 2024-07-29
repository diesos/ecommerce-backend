package com.Side.Project.ecommerce_backend.controller.auth;

import com.Side.Project.ecommerce_backend.api.models.RegistrationBody;
import com.Side.Project.ecommerce_backend.exception.UserAlreadyExist;
import com.Side.Project.ecommerce_backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity registerUser(@Valid @RequestBody RegistrationBody registrationBody) {
        try {
            userService.registerUser(registrationBody);
            return ResponseEntity.ok().build();
        } catch (UserAlreadyExist e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }
}