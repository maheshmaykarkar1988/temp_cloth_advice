package eu.toon.service;

import eu.toon.wrapper.TemperatureAdviceDetailsResponse;
import eu.toon.wrapper.WeatherClothingAdviceResponse;
import eu.toon.wrapper.WeatherClothingAdviceRequest;

import java.util.List;

/**
 * Represents method declaration of clothing advice
 * Created by Mahesh Maykarkar on 27/02/19.
 */

public interface AdviceRestService {

    public List<TemperatureAdviceDetailsResponse> getAllPairs();
    public TemperatureAdviceDetailsResponse getPairDetails(int id);
    public void deletePairDetails(int id);
    public void createWeatherClothingAdvicePair(WeatherClothingAdviceRequest weatherClothingAdviceRequest);
    public void updateWeatherAdvicePair(WeatherClothingAdviceRequest weatherDetailRequest);
    WeatherClothingAdviceResponse getWeatherAdvice(String city);
}
