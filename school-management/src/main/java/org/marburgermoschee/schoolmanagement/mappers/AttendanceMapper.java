package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.marburgermoschee.schoolmanagement.dtos.AttendanceDto;
import org.marburgermoschee.schoolmanagement.entities.Attendance;

@Mapper(componentModel = "spring", uses = LessonMapper.class)
public interface AttendanceMapper {
    AttendanceDto toDto(Attendance attendance);
}

