package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.marburgermoschee.schoolmanagement.dtos.ClassDto;
import org.marburgermoschee.schoolmanagement.dtos.ClassWithTeachersDto;
import org.marburgermoschee.schoolmanagement.dtos.RegisterNewClassRequest;
import org.marburgermoschee.schoolmanagement.entities.Class;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassDto toDto(Class classField);

    @Mapping(source = "type", target = "type")
    Class register(RegisterNewClassRequest request);

}
