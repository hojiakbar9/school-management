package org.marburgermoschee.schoolmanagement.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.marburgermoschee.schoolmanagement.entities.ClassType;

@Data
public class RegisterNewClassRequest {
    @NotNull(message = "the type of the class is required")
    private ClassType type;
}
