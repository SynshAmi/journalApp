package net.engineeringdigest.journalApp.service;

import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {
    private static final String apiKey="4b2a60e4c67c67b98efe015d8676c39c";

    private static final String API="http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherResponse getWeather(String city)
    {
        String finalAPI = API.replace("API_KEY", apiKey).replace("CITY", city);
        ResponseEntity<WeatherResponse> exchange = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
        WeatherResponse body = exchange.getBody();
        return body;
    }
/*public WeatherResponse getWeather(String city)
{
    String finalAPI = API.replace("API_KEY", apiKey).replace("CITY", city);

    ResponseEntity<String> exchange =
            restTemplate.exchange(finalAPI, HttpMethod.GET, null, String.class);

    System.out.println("========= RAW WEATHER API RESPONSE =========");
    System.out.println(exchange.getBody());
    System.out.println("============================================");

    return null; // TEMPORARY
}*/

}

//JSON → Java object = Deserialization
//Java object → JSON = Serialization