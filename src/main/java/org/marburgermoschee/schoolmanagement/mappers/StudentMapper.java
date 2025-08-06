package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.marburgermoschee.schoolmanagement.dtos.RegisterStudentRequest;
import org.marburgermoschee.schoolmanagement.dtos.StudentDto;
import org.marburgermoschee.schoolmanagement.entities.Student;

@Mapper(componentModel = "spring", uses = {ParentMapper.class, AttendanceMapper.class})
public interface StudentMapper {
    @Mapping(source = "parent", target = "parentDto")
    StudentDto toDto(Student student);
    Student toEntity(RegisterStudentRequest request);
    Student update(RegisterStudentRequest request, @MappingTarget Student student);
}
