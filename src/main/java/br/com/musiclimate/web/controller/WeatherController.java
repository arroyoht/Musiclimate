package br.com.musiclimate.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.musiclimate.client.service.WeatherApiIntegrationService;
import br.com.musiclimate.resource.WeatherResource;

@RestController
@RequestMapping(value = "/weather", produces = MediaType.APPLICATION_JSON_VALUE)
public class WeatherController {

	@Autowired
	WeatherApiIntegrationService weatherService;

	@GetMapping("/city/{city}")
	public ResponseEntity<WeatherResource> getWeatherByCity(@PathVariable("city") String cityName) {
		return ResponseEntity.ok(weatherService.getWeatherByCity(cityName));
	}

	@GetMapping("/coordinate")
	public ResponseEntity<WeatherResource> getWeatherByCoordinates(@RequestParam("lat") Double latitude,
			@RequestParam("lon") Double longitude) {
		return ResponseEntity.ok(weatherService.getWeatherByCoordinates(latitude, longitude));
	}
}