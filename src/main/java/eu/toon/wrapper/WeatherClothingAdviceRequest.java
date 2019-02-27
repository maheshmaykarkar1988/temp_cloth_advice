package eu.toon.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Wrapper class represent to add OR update weather advice clothing pair
 * Created by Mahesh Maykarkar on 27/02/19.
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherClothingAdviceRequest {

    private int id;
    @NotNull(message = "Advice must not be null")
    @Size(min = 1, message = "Advice must not be blank")
    private String advice;
    private double minTemperatureRange;
    private double maxTemperatureRange;
}
