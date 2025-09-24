package org.marburgermoschee.schoolmanagement.dtos;

import lombok.Data;

@Data
public class UserDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
