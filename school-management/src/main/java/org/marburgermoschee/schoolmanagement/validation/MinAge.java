package org.marburgermoschee.schoolmanagement.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MinAgeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface MinAge {
    String message() default "Child must be at least {value} years old";
    int value();
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}