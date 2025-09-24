package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.marburgermoschee.schoolmanagement.dtos.LessonDto;
import org.marburgermoschee.schoolmanagement.entities.Lesson;

@Mapper(componentModel = "spring")
public interface LessonMapper {
    LessonDto toDto(Lesson lesson);


}
