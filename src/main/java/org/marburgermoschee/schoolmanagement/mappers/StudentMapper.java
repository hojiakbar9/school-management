package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.marburgermoschee.schoolmanagement.dtos.RegisterStudentRequest;
import org.marburgermoschee.schoolmanagement.dtos.StudentDto;
import org.marburgermoschee.schoolmanagement.entities.Student;
import org.marburgermoschee.schoolmanagement.services.UpdateStudentRequest;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    @Mapping(source = "parent.user.firstName", target = "parent.firstName")
    @Mapping(source = "parent.user.firstName", target = "parent.lastName")
    StudentDto toDto(Student student);
    Student toEntity(RegisterStudentRequest request);
    Student update(UpdateStudentRequest request, @MappingTarget Student student);
}
