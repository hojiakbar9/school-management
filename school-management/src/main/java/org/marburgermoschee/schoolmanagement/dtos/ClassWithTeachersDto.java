package org.marburgermoschee.schoolmanagement.dtos;

import lombok.Data;
import org.marburgermoschee.schoolmanagement.entities.ClassType;

import java.util.List;

@Data
public class ClassWithTeachersDto {
    private Integer id;
    private List<ClassTeacherDto> teachers;
    private ClassType type;
}
