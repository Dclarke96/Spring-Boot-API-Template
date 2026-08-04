package com.dylanclarke.springbootapitemplate.exception;

/**
 * Exception thrown when business logic validation fails.
 */
public class ValidationException extends RuntimeException {
    
    private final String fieldName;

    public ValidationException(String message) {
        super(message);
        this.fieldName = null;
    }

    public ValidationException(String message, String fieldName, Object fieldValue) {
        super(message);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
