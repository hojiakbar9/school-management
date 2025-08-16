package org.marburgermoschee.schoolmanagement.exceptions;

public class DuplicateEntryException extends RuntimeException {
    public  DuplicateEntryException(String message) {
        super(message);
    }
}
