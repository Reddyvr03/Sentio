package net.engineeringdigest.journalApp.Service;

import lombok.NoArgsConstructor;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@NoArgsConstructor(force = true)
public class WeatherService {


    @Value("${weather_api_key}")
    private final String apiKey;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RedisService redisService;


    public WeatherResponse getWeather(String city){
        WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
        if(weatherResponse!=null){
            return weatherResponse;
        }else {
            String replace = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(PlaceHolders.CITY,city).replace(PlaceHolders.API_KEY,apiKey);
            ResponseEntity<WeatherResponse> exchange = restTemplate.exchange(replace, HttpMethod.GET, null, WeatherResponse.class);
            WeatherResponse responseBody = exchange.getBody();
            if (responseBody!=null){
                redisService.set("weather_of_"+ city, responseBody,300l);
            }
            return responseBody;
        }


    }
}
