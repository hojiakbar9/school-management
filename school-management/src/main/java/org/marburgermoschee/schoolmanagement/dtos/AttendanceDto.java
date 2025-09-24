package org.marburgermoschee.schoolmanagement.dtos;

import lombok.Data;
import org.marburgermoschee.schoolmanagement.entities.AttendanceStatus;
@Data
public class AttendanceDto {
    private LessonDto lesson;
    private AttendanceStatus status;
}
