package eu.toon.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

/**
 * Wrapper class represent third party openweathermap response
 * Created by Mahesh Maykarkar on 27/02/19.
 */

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "coord",
        "weather",
        "base",
        "main",
        "visibility",
        "wind",
        "clouds",
        "dt",
        "sys",
        "id",
        "name",
        "cod"
})
@Data
public class WeatherResponse {

    @JsonProperty("coord")
    public Coord coord;

    @JsonProperty("weather")
    public List<Weather> weather = null;

    @JsonProperty("base")
    public String base;

    @JsonProperty("main")
    public Main main;

    @JsonProperty("visibility")
    public Integer visibility;

    @JsonProperty("wind")
    public Wind wind;

    @JsonProperty("clouds")
    public Clouds clouds;

    @JsonProperty("dt")
    public Integer dt;

    @JsonProperty("sys")
    public Sys sys;

    @JsonProperty("id")
    public Integer id;

    @JsonProperty("name")
    public String name;

    @JsonProperty("cod")
    public Integer cod;
}
