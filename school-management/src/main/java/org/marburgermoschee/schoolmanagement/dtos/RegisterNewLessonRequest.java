package org.marburgermoschee.schoolmanagement.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class RegisterNewLessonRequest {
    @NotNull(message = "Date is required")
    private LocalDate date;
    private String topic;
}
