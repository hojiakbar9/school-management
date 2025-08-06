package org.marburgermoschee.schoolmanagement.dtos;

import lombok.Data;
import org.marburgermoschee.schoolmanagement.entities.GenderType;

import java.util.HashSet;
import java.util.Set;
import java.time.LocalDate;
@Data
public class StudentDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private GenderType gender;
    private LocalDate dateOfBirth;
    private ParentDto parentDto;
    private Boolean active;
    private Set<AttendanceDto> attendances = new HashSet<>();
    private Set<PaymentDto> payments =  new HashSet<>();
    private Set<StudentClassDto>  classes = new  HashSet<>();
}
