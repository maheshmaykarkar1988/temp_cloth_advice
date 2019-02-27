
package eu.toon.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "temp",
    "pressure",
    "humidity",
    "temp_min",
    "temp_max"
})
@Builder
public class Main {

    @JsonProperty("temp")
    public Double temp;

    @JsonProperty("pressure")
    public Integer pressure;

    @JsonProperty("humidity")
    public Integer humidity;

    @JsonProperty("temp_min")
    public Double tempMin;

    @JsonProperty("temp_max")
    public Double tempMax;

}
