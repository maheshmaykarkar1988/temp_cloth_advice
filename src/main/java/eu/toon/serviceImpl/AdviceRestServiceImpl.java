package eu.toon.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import eu.toon.dao.TemperatureAdviceDetailsDAO;
import eu.toon.domain.TemperatureAdviceDetails;
import eu.toon.exceptions.NoRecordFoundException;
import eu.toon.exceptions.BusinessValidationException;
import eu.toon.service.AdviceRestService;
import eu.toon.wrapper.TemperatureAdviceDetailsResponse;
import eu.toon.wrapper.WeatherClothingAdviceResponse;
import eu.toon.wrapper.WeatherClothingAdviceRequest;
import eu.toon.wrapper.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Represents actual implementations of CRUD operations related to clothing advice
 * Created by Mahesh Maykarkar on 27/02/19.
 */

@Service
public class AdviceRestServiceImpl implements AdviceRestService {

    private static final Logger logger = LoggerFactory.getLogger(AdviceRestServiceImpl.class);

    @Autowired
    TemperatureAdviceDetailsDAO temperatureAdviceDetailsDAO;

    @Value("${weathermap.apikey}")
    private String weatherAPIKey;

    @Value("${weathermap.units}")
    private String units;

    @Value("${weathermap.mode}")
    private String mode;

    @Value("${weathermap.url}")
    private String url;

    @Value("${city.error.message}")
    private String cityErrorMesssage;

    @Value("${internal.generic.error.message}")
    private String genericErrorMessage;

    @Value("${advice.error.message}")
    private String adviceErrorMessage;

    @Value("${weather.pair.error.message}")
    private String weatherPairErrorMessage;

    @Value("${weather.pair.exists}")
    private String weatherPairExists;

    @Value("${invalid.weather.pair.id}")
    private String invalidWeatherPair;


    /**
     * Return all the temperate advice clothing pairs
     * @return {@link List<TemperatureAdviceDetailsResponse>}
     */
    @Override
    public List<TemperatureAdviceDetailsResponse> getAllPairs() {
        List<TemperatureAdviceDetails> temperatureAdviceDetails = temperatureAdviceDetailsDAO.findAll();

        if (CollectionUtils.isEmpty(temperatureAdviceDetails)) {
            throw new NoRecordFoundException(weatherPairErrorMessage);
        }

        List<TemperatureAdviceDetailsResponse> adviceDetailsResponses = temperatureAdviceDetails.stream().map(t -> {
            TemperatureAdviceDetailsResponse temperatureAdviceDetailsResponse = TemperatureAdviceDetailsResponse.builder().advice(t.getAdvice()).maxTemperatureRange(t.getMaxTemperature()).minTemperatureRange(t.getMinTemperature()).build();
            return temperatureAdviceDetailsResponse;
        }).collect(Collectors.toList());

        return adviceDetailsResponses;
    }

    /**
     * Returns temperature advice clothing details as per given id
     * @param id
     * @return @{@link TemperatureAdviceDetailsResponse}
     */
    @Override
    public TemperatureAdviceDetailsResponse getPairDetails(int id) {
        Optional<TemperatureAdviceDetails> pairDetails = temperatureAdviceDetailsDAO.findById(id);

        pairDetails.orElseThrow(() -> new NoRecordFoundException(weatherPairErrorMessage));

        TemperatureAdviceDetailsResponse temperatureAdviceDetailsResponse = TemperatureAdviceDetailsResponse.builder().advice(pairDetails.get().getAdvice()).maxTemperatureRange(pairDetails.get().getMaxTemperature()).minTemperatureRange(pairDetails.get().getMinTemperature()).build();

        return temperatureAdviceDetailsResponse;
    }

    /**
     * Removes temperature advice clothing pair for given id
     * @param id
     */
    @Override
    public void deletePairDetails(int id) {
        Optional<TemperatureAdviceDetails> pairDetails = temperatureAdviceDetailsDAO.findById(id);

        pairDetails.orElseThrow(() -> new NoRecordFoundException(weatherPairErrorMessage));

        temperatureAdviceDetailsDAO.deleteById(id);
    }

    /**
     * Created temperature advice clothing pair. Dont pass id in request payload
     * @param @{@link WeatherClothingAdviceRequest}
     */
    @Override
    @Transactional
    public void createWeatherClothingAdvicePair(WeatherClothingAdviceRequest weatherDetailRequest) {
        int count = temperatureAdviceDetailsDAO.findByTemperatureRange(weatherDetailRequest.getMinTemperatureRange(), weatherDetailRequest.getMaxTemperatureRange());

       if(count > 0){
          throw new BusinessValidationException(String.format(weatherPairExists, weatherDetailRequest.getMinTemperatureRange(), weatherDetailRequest.getMaxTemperatureRange()));
       }

        TemperatureAdviceDetails temperatureAdviceDetails = new TemperatureAdviceDetails(weatherDetailRequest);

        temperatureAdviceDetailsDAO.save(temperatureAdviceDetails);
    }

    /**
     * Updates temperature advice clothing pair. Pass complete request body
     * @param @{@link WeatherClothingAdviceRequest}
     */
    @Override
    @Transactional
    public void updateWeatherAdvicePair(WeatherClothingAdviceRequest weatherDetailRequest) {
        Optional<TemperatureAdviceDetails> adviceDetails = temperatureAdviceDetailsDAO.findById(weatherDetailRequest.getId());

        adviceDetails.orElseThrow(() -> new NoRecordFoundException(String.format(invalidWeatherPair, weatherDetailRequest.getId())));

        TemperatureAdviceDetails temperatureAdviceDetails = adviceDetails.get();

        temperatureAdviceDetails.getUpdatedAdviceTemperatureDetails(temperatureAdviceDetails,weatherDetailRequest);

        temperatureAdviceDetailsDAO.save(temperatureAdviceDetails);
    }

    /**
     * Returns temperature advice clothing details based on city name.
     * @param city
     * @return {@link WeatherClothingAdviceResponse}
     */
    @Override
    public WeatherClothingAdviceResponse getWeatherAdvice(String city) {
        if (StringUtils.isEmpty(city.trim())) {
            throw new BusinessValidationException(cityErrorMesssage);
        }

        Double currentTemperature = getCurrentTemp(city);

        String advice = temperatureAdviceDetailsDAO.getAdvice(currentTemperature);

        if (StringUtils.isEmpty(advice)) {
            throw new BusinessValidationException(String.format(adviceErrorMessage, city));
        }

        WeatherClothingAdviceResponse weatherAdviceResponse = WeatherClothingAdviceResponse.builder().advice(advice).city(city).currentTemperature(currentTemperature).build();

        return weatherAdviceResponse;
    }

    /**
     * Returns current temperature by calling third party service.
     * @param city
     * @return temperature
     */
    private Double getCurrentTemp(String city) {
        WeatherResponse weatherResponse = null;

        try {
            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(url).
                    queryParam("q", city).
                    queryParam("units", units).
                    queryParam("appid", weatherAPIKey);

            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

            Response response = invocationBuilder.get();

            if (response.getStatus() != HttpStatus.OK.value()) {
                logger.info("HTTP status code doesn't match with 200");
                throw new BusinessValidationException(genericErrorMessage);
            }

            // convert response to POJO using GSON
            Gson gson = new Gson();
            weatherResponse = gson.fromJson(response.readEntity(String.class).toString(), new TypeToken<WeatherResponse>() {
            }.getType());

        } catch (Exception e) {
            logger.info(e.getMessage());
            throw new BusinessValidationException(genericErrorMessage);
        }


        return weatherResponse.getMain().temp;


    }
}

