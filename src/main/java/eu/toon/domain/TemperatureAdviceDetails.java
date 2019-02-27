package eu.toon.domain;

import eu.toon.wrapper.WeatherClothingAdviceRequest;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity class to represent clothing advice details on temperature
 * Created by Mahesh Maykarkar on 27/02/19.
 */

@Data
@Entity
@Table(name = "temperature_advice_details")
public class TemperatureAdviceDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private int id;

    @Column(name = "min_temperature", nullable = false)
    private double minTemperature;

    @Column(name = "max_temperature", nullable = false)
    private double maxTemperature;

    @Column(name = "advice")
    private String advice;

    public TemperatureAdviceDetails() {
    }


    public TemperatureAdviceDetails(WeatherClothingAdviceRequest weatherDetailRequest) {
        this.minTemperature = weatherDetailRequest.getMinTemperatureRange();
        this.maxTemperature = weatherDetailRequest.getMaxTemperatureRange();
        this.advice = weatherDetailRequest.getAdvice();
    }

    public void getUpdatedAdviceTemperatureDetails(TemperatureAdviceDetails temperatureAdviceDetails,WeatherClothingAdviceRequest weatherDetailRequest) {
        temperatureAdviceDetails.setMinTemperature(weatherDetailRequest.getMinTemperatureRange());
        temperatureAdviceDetails.setMaxTemperature(weatherDetailRequest.getMaxTemperatureRange());
        temperatureAdviceDetails.setAdvice(weatherDetailRequest.getAdvice());
    }
}
