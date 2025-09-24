package org.marburgermoschee.schoolmanagement.dtos;

import lombok.Data;
import org.marburgermoschee.schoolmanagement.entities.ClassType;

@Data
public class ClassDto {
    private Integer id;
    private ClassType type;
}
