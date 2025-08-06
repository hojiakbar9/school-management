package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.marburgermoschee.schoolmanagement.dtos.ClassDto;
import org.marburgermoschee.schoolmanagement.entities.Class;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassDto toDto(Class classField);
}
