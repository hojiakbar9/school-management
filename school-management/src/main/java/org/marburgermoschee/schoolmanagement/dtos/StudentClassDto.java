package org.marburgermoschee.schoolmanagement.dtos;

import lombok.Data;
import org.marburgermoschee.schoolmanagement.entities.ClassType;

@Data
public class StudentClassDto {
    private Integer classId;
    private ClassType classType;
}
