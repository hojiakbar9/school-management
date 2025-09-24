package org.marburgermoschee.schoolmanagement.dtos;

import lombok.Data;

@Data
public class ClassTeacherDto {
    private String name;

    public ClassTeacherDto(String name) {
        this.name = name;
    }
}
