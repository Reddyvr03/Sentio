package net.engineeringdigest.journalApp.api.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class WeatherResponse{
    private Current current;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Current{
        public int temperature;
        @JsonProperty("weather_descriptions")
        public List<String> weatherDescriptions;
        public int feelslike;
    }
}

