package com.reddyclinic.doctorportal.exception;

/**
 * Custom exception for invalid login credentials.
 */
public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String message) {
        super(message);
    }
}
