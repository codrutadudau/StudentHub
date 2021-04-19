package com.project.StudentHub.exception;

@SuppressWarnings("serial")
public class EmailExistsException extends Exception {

    public EmailExistsException(final String message) {
        super(message);
    }
}
