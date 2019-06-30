package br.com.musiclimate.service;

import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.musiclimate.client.resource.WeatherResource;
import br.com.musiclimate.client.service.WeatherApiIntegrationService;
import br.com.musiclimate.error.ResourceNotFoundException;

@Service
public class TemperatureService {

	@Autowired 
	private WeatherApiIntegrationService weatherApiService;
	
	/**
	 * Method responsible for getting the temperature given a city name
	 * 
	 * @param city
	 * @return temperature value
	 */
	@Cacheable(value = "temperatureByCity", key = "#city")
	public Double getTemperatureByCity(String city) {
		if (StringUtils.isEmpty(city)) {
			throw new InvalidParameterException("City name is required");
		}

		WeatherResource resource = weatherApiService.getWeatherByCity(city);
		if (resource == null || resource.getTemperature() == null) {
			throw new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND);
		}

		return resource.getWeather().getTemperature();
	}

	/**
	 * Method responsible for getting the temperature given a locations coordinate
	 * 
	 * @param lat latitude
	 * @param lon longitude
	 * @return temperature value
	 */
	@Cacheable(value = "temperatureByCoordinate", key = "{#lat,#lon}", unless = "#result == null")
	public Double getTemperatureByCoordinate(Double lat, Double lon) {
		if (lat == null || lon == null) {
			throw new InvalidParameterException("location coordinates is required");
		}

		WeatherResource resource = weatherApiService.getWeatherByCoordinates(lat, lon);
		if (resource == null || resource.getTemperature() == null) {
			throw new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND);
		}

		return resource.getWeather().getTemperature();
	}
}
