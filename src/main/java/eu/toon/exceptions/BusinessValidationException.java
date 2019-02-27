package eu.toon.exceptions;

import lombok.Data;

/**
 * Custom exception class to handle business validation
 * Created by Mahesh Maykarkar on 28/02/19.
 */

@Data
public class BusinessValidationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorMessage;

    public BusinessValidationException() {
        super();
    }

    public BusinessValidationException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
