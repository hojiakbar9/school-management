package org.marburgermoschee.schoolmanagement.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum GenderType {
    MALE,
    FEMALE;

    @JsonCreator
    public static GenderType fromString(String value) {
        if (value == null) return null;
        return GenderType.valueOf(value.trim().toUpperCase());
    }
}
