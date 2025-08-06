package org.marburgermoschee.schoolmanagement.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.marburgermoschee.schoolmanagement.entities.GenderType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class RegisterStudentRequest {
    @NotBlank(message = "first name is required")
    @Size(max = 255, message = "first name should be less than 255 characters")
    private String firstName;

    @NotBlank(message = "last name is required")
    @Size(max = 255, message = "last name should be less than 255 characters")
    private String lastName;

    @NotNull(message = "gender is required")
    private GenderType gender;

    @NotNull(message = "date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;
    private Integer parentId;
    
    private Boolean active = Boolean.TRUE;

}
