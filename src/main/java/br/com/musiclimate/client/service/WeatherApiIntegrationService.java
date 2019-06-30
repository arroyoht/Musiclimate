package br.com.musiclimate.client.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.musiclimate.resource.WeatherResource;

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
	@Cacheable(value = "weatherByCity", key = "#city")
	WeatherResource getWeatherByCity(@PathVariable("city") String city);

	/**
	 * Method gets weather information according to given coordinates
	 * 
	 * @param lat latitude
	 * @param lon longitude
	 * @return weather information resource
	 */
	@GetMapping(value = "/weather?units=metric&lat={lat}&lon={lon}" + "&APPID=" + apiKey)
	@Cacheable(value = "weatherByCoordinates", key = "{#lat,#lon}")
	WeatherResource getWeatherByCoordinates(@PathVariable("lat") Double lat, @PathVariable("lon") Double lon);
}
