package org.marburgermoschee.schoolmanagement.dtos;

import lombok.Data;
import org.marburgermoschee.schoolmanagement.entities.GenderType;

import java.time.LocalDate;
@Data
public class StudentDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private GenderType gender;
    private LocalDate dateOfBirth;
    private UserDto parent;
    private Boolean active;

}
