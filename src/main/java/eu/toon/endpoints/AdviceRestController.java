package eu.toon.endpoints;

import eu.toon.service.AdviceRestService;
import eu.toon.wrapper.TemperatureAdviceDetailsResponse;
import eu.toon.wrapper.WeatherClothingAdviceResponse;
import eu.toon.wrapper.WeatherClothingAdviceRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Rest controller having all CRUD operation related clothing advice
 * Created by Mahesh Maykarkar on 27/02/19.
 */

@Api(value = "user", description = "Weather clothing advice API's", tags = "User API")
@RestController
@RequestMapping(value = "/weather")
public class AdviceRestController {

    @Autowired
    AdviceRestService adviceRestService;

    @GetMapping(produces = "application/json")
    @ApiOperation(value = "Returns all possible pairs of weather", response = TemperatureAdviceDetailsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TemperatureAdviceDetailsResponse.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "No weather clothing advice details found"),
    }
    )
    public ResponseEntity getAllPairs() {
        List<TemperatureAdviceDetailsResponse> allPairs = adviceRestService.getAllPairs();
        return new ResponseEntity<List<TemperatureAdviceDetailsResponse>>(allPairs, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    @ApiOperation(value = "Returns all possible pairs of weather", response = TemperatureAdviceDetailsResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = TemperatureAdviceDetailsResponse.class),
            @ApiResponse(code = 404, message = "No weather clothing advice details found"),
    }
    )
    public ResponseEntity getPairDetails(@Valid @PathVariable("id") int id) {
        TemperatureAdviceDetailsResponse pairDetails = adviceRestService.getPairDetails(id);
        return new ResponseEntity<TemperatureAdviceDetailsResponse>(pairDetails, HttpStatus.OK);
    }

    @GetMapping(value = "/advice", produces = "application/json")
    @ApiOperation(value = "Returns clothing advice for given city", response = WeatherClothingAdviceResponse.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = WeatherClothingAdviceResponse.class),
            @ApiResponse(code = 422, message = "Name of city must not be null or empty"),
            @ApiResponse(code = 422, message = "No weather clothing advice details found for city"),
            @ApiResponse(code = 422, message = "There is an issue processing your request.Please contact support"),
    }
    )
    public ResponseEntity getWeatherAdvice(@RequestParam(required = true) String city) {
        WeatherClothingAdviceResponse weatherAdvice = adviceRestService.getWeatherAdvice(city);
        return new ResponseEntity<WeatherClothingAdviceResponse>(weatherAdvice, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    @ApiOperation(value = "Deletes weather clothing advice pair for given id", code = 200)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "No pairs were found related to weather"),
    }
    )
    public ResponseEntity deleteWeatherPair(@Valid @PathVariable("id") int id) {
        adviceRestService.deletePairDetails(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    @ApiOperation(value = "Creates weather clothing advice pair", code = 201)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 422, message = "Weather clothing advice pair with minTemp and maxTemp already exists"),
    }
    )
    public ResponseEntity createWeatherClothingAdvicePair(@Valid @RequestBody WeatherClothingAdviceRequest weatherClothingAdviceRequest) {
        adviceRestService.createWeatherClothingAdvicePair(weatherClothingAdviceRequest);
        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    @ApiOperation(value = "Updates Weather clothing advice pair details")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Invalid weather clothing advice id.Please provide correct id to update the record"),
    }
    )
    public ResponseEntity updateWeatherAdvicePair(@Valid @RequestBody WeatherClothingAdviceRequest weatherDetailRequest) {
        adviceRestService.updateWeatherAdvicePair(weatherDetailRequest);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
