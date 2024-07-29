package com.Side.Project.ecommerce_backend.controller.auth;

import com.Side.Project.ecommerce_backend.api.models.RegistrationBody;
import com.Side.Project.ecommerce_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody RegistrationBody registrationBody){
        userService.registerUser(registrationBody);
    }
}
