package org.marburgermoschee.schoolmanagement.services;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.marburgermoschee.schoolmanagement.validation.MinAge;

import java.time.LocalDate;

@Data
public class UpdateStudentRequest {
    @NotBlank(message = "first name is required")
    @Size(max = 255, message = "first name should be less than 255 characters")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Size(max = 255, message = "last name should be less than 255 characters")
    private String lastName;

    @MinAge(value = 5)
    @NotNull(message = "date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    private Boolean active = Boolean.TRUE;

}
