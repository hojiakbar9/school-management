package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marburgermoschee.schoolmanagement.dtos.ParentDto;
import org.marburgermoschee.schoolmanagement.entities.Parent;

@Mapper(componentModel = "spring")
public interface ParentMapper {
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.phone", target = "phone")
    ParentDto toDto(Parent entity);
}
