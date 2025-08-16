package org.marburgermoschee.schoolmanagement.entities;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ClassType {
    ARABIC,
    QURAN;

    @JsonCreator
    public static ClassType fromString(String value) {
        if (value == null) return null;
        return ClassType.valueOf(value.trim().toUpperCase());
    }
}
