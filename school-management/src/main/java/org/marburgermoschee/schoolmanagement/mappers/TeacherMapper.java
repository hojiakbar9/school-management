package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marburgermoschee.schoolmanagement.dtos.ClassTeacherDto;
import org.marburgermoschee.schoolmanagement.entities.Teacher;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    @Mapping(source = "user.firstName", target = "name")
    ClassTeacherDto toClassTeacherDto(Teacher teacher);
}
