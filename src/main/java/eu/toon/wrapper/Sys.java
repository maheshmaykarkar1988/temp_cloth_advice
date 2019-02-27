
package eu.toon.wrapper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "type",
    "id",
    "message",
    "country",
    "sunrise",
    "sunset"
})
@Builder
public class Sys {

    @JsonProperty("type")
    public Integer type;

    @JsonProperty("id")
    public Integer id;

    @JsonProperty("message")
    public Double message;

    @JsonProperty("country")
    public String country;

    @JsonProperty("sunrise")
    public Integer sunrise;

    @JsonProperty("sunset")
    public Integer sunset;

}
