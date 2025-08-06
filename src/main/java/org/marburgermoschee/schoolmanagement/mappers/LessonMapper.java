package org.marburgermoschee.schoolmanagement.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.marburgermoschee.schoolmanagement.dtos.LessonDto;
import org.marburgermoschee.schoolmanagement.entities.Lesson;

@Mapper(componentModel = "spring", uses= ClassMapper.class)
public interface LessonMapper {
    @Mapping(target = "classDto", source = "classField")
    LessonDto toDto(Lesson lesson);
}
