package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.marburgermoschee.schoolmanagement.dtos.UpdateParentRequest;
import org.marburgermoschee.schoolmanagement.dtos.UserDto;
import org.marburgermoschee.schoolmanagement.dtos.RegisterParentRequest;
import org.marburgermoschee.schoolmanagement.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User register(RegisterParentRequest request);

    User update(UpdateParentRequest request, @MappingTarget  User user);
}
