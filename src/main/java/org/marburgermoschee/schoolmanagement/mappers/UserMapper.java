package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.marburgermoschee.schoolmanagement.dtos.*;
import org.marburgermoschee.schoolmanagement.entities.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User registerParent(RegisterParentRequest request);
    User registerTeacher(RegisterTeacherRequest request);
    User updateParent(UpdateParentRequest request, @MappingTarget  User user);
    User updateTeacher(UpdateTeacherRequest request, @MappingTarget  User user);
}
