package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marburgermoschee.schoolmanagement.dtos.UserDto;
import org.marburgermoschee.schoolmanagement.dtos.RegisterParentRequest;
import org.marburgermoschee.schoolmanagement.entities.Parent;

@Mapper(componentModel = "spring")
public interface ParentMapper {
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "user.phone", target = "phone")
    UserDto toDto(Parent entity);

    UserDto register(RegisterParentRequest request);
}
