package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marburgermoschee.schoolmanagement.dtos.ClassDto;
import org.marburgermoschee.schoolmanagement.dtos.ClassStudentDto;
import org.marburgermoschee.schoolmanagement.dtos.ClassWithTeachersDto;
import org.marburgermoschee.schoolmanagement.dtos.RegisterNewClassRequest;
import org.marburgermoschee.schoolmanagement.entities.Class;
import org.marburgermoschee.schoolmanagement.entities.Student;

@Mapper(componentModel = "spring", uses = TeacherMapper.class)
public interface ClassMapper {

    ClassDto toDto(Class classField);

    @Mapping(source = "type", target = "type")
    Class register(RegisterNewClassRequest request);


    ClassWithTeachersDto toClassWithTeachersDto(Class cl);

    ClassStudentDto toClassStudentDto(Student student);
}
