package br.com.musiclimate.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.musiclimate.client.resource.WeatherResource;

@FeignClient(name = "weatherAPI", url = "${api.weather.url}")
public interface WeatherApiIntegrationService {

	final String apiKey = "${api.weather.key}";

	/**
	 * Method gets city weather information
	 * 
	 * @param city name of the city to query the weather API
	 * @return weather information resource
	 */
	@GetMapping(value = "/weather?units=metric&q={city}" + "&APPID=" + apiKey)
	WeatherResource getWeatherByCity(@PathVariable("city") String city);

	/**
	 * Method gets weather information according to given coordinates
	 * 
	 * @param lat latitude
	 * @param lon longitude
	 * @return weather information resource
	 */
	@GetMapping(value = "/weather?units=metric&lat={lat}&lon={lon}" + "&APPID=" + apiKey)
	WeatherResource getWeatherByCoordinates(@PathVariable("lat") Double lat, @PathVariable("lon") Double lon);
}
