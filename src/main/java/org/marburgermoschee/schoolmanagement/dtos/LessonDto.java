package org.marburgermoschee.schoolmanagement.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LessonDto {
    private Integer id;
    private ClassDto classDto;
    private LocalDate date;
    private String topic;
}
