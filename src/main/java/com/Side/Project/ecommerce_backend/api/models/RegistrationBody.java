package com.Side.Project.ecommerce_backend.api.models;

import jakarta.validation.constraints.*;

public class RegistrationBody {

    @NotBlank
    @NotNull
    @Pattern(regexp = "^[A-Za-z][A-Za-z0-9-_]{3,23}$")
    @Size(min = 3, max = 24)  // Adjusted max value to align with regexp
    private String username;

    @NotBlank
    @NotNull
    @Email
    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")
    @Size(max = 255)
    private String email;

    @NotNull
    @NotBlank
    @Size(min = 8, max = 24)
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,24}$")
    private String password;

    @NotNull
    @NotBlank
    private String firstName;

    @NotNull
    @NotBlank
    private String lastName;

    public String getUsername() {
        return username;
    }


    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
