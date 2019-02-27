package eu.toon.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TemperatureAdviceDetailsResponse {
    private double minTemperatureRange;
    private double maxTemperatureRange;
    private String advice;
}
