package eu.toon.exceptions;

import lombok.Data;

/**
 * Custom exception class to handle records not found scenarios
 * Created by Mahesh Maykarkar on 28/02/19.
 */

@Data
public class NoRecordFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String errorMessage;

    public NoRecordFoundException() {
        super();
    }

    public NoRecordFoundException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }
}
