package eu.toon.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Data;

/**
 * Wrapper class representing weather clothing advice response
 * Created by Mahesh Maykarkar on 28/02/19.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "city",
        "currentTemperature",
        "advice"
})
@Data
@Builder
public class WeatherClothingAdviceResponse {
    private String city;
    private double currentTemperature;
    private String advice;
}
