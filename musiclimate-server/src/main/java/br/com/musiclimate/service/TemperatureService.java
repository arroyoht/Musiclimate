package br.com.musiclimate.service;

import java.security.InvalidParameterException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.musiclimate.client.resource.WeatherResource;
import br.com.musiclimate.client.service.WeatherApiIntegrationService;
import br.com.musiclimate.error.ResourceNotFoundException;

@Service
public class TemperatureService {

	private static Double MIN_TEMP = -40.0d;
	private static Double RANGE = 100.0d;
	
	@Autowired
	private WeatherApiIntegrationService weatherApiService;

	/**
	 * Method responsible for getting the temperature given a city name
	 * 
	 * @param city
	 * @return temperature value
	 */
	@Cacheable(value = "temperatureByCity", key = "#city")
	@HystrixCommand(commandKey = "getTemperatureByCity", fallbackMethod = "getFallbackTemperature", ignoreExceptions = {
			ResourceNotFoundException.class, InvalidParameterException.class })
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
	@Cacheable(value = "temperatureByCoordinate", key = "{#lat,#lon}")
	@HystrixCommand(commandKey = "getTemperatureByCoordinate", fallbackMethod = "getFallbackTemperature", ignoreExceptions = {
			ResourceNotFoundException.class, InvalidParameterException.class })
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
	
	public Double getFallbackTemperature(String city) {
		return getFallbackTemperature();
	}
	
	public Double getFallbackTemperature(Double lat, Double lon) {
		return getFallbackTemperature();
	}
	
	/**
	 * Generates random Double ranging from (MIN_TEMP) to (RANGE + MIN_TEMP) 
	 * @return random temperature
	 */
	private Double getFallbackTemperature() {
		return (new Random().nextDouble() * RANGE) + MIN_TEMP;
	}
}
