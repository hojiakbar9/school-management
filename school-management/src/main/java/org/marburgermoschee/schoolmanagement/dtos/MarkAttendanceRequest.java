package org.marburgermoschee.schoolmanagement.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.marburgermoschee.schoolmanagement.entities.AttendanceStatus;

@Data
public class MarkAttendanceRequest {
    @NotNull(message = "student id is required")
    private Integer studentId;

    @NotNull(message = "status is required")
    private AttendanceStatus status;
}
