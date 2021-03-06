package eu.toon.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Class representing error message details
 * Created by Mahesh Maykarkar on 28/02/19.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ErrorResponse {

    private int errorCode;
    private String field;
    private String message;

}
