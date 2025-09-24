package org.marburgermoschee.schoolmanagement.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterParentRequest {
    @NotBlank(message = "first name is required")
    @Size(min = 1, message = "first name must be at least 1 char long")
    private String firstName;

    @NotBlank(message = "first name is required")
    @Size(min = 1, message = "first name must be at least 1 char long")
    private String lastName;
    @NotBlank(message = "email is required")
    @Email(message = "email should be valid")
    private String email;

    @NotBlank(message = "phone is required")
    private String phone;
}
