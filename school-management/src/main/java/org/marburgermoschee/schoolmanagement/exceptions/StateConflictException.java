package org.marburgermoschee.schoolmanagement.exceptions;

public class StateConflictException extends RuntimeException {
    public StateConflictException(String message) {
        super(message);
    }
}
