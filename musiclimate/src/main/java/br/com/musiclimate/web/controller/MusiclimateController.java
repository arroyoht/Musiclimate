package br.com.musiclimate.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.musiclimate.service.MusiclimateService;

@RestController
@RequestMapping(value = "/musiclimate", produces = MediaType.APPLICATION_JSON_VALUE)
public class MusiclimateController {

	@Autowired
	private MusiclimateService musiclimateService;

	@RequestMapping(value = "/city/{city}")
	public ResponseEntity<List<String>> getPlaylistByCity(@PathVariable("city") String city) {
		return ResponseEntity.ok(musiclimateService.getPlaylistByCity(city));
	}

	@GetMapping("/coordinates")
	public ResponseEntity<List<String>> getWeatherByCoordinates(@RequestParam("lat") Double latitude,
			@RequestParam("lon") Double longitude) {
		return ResponseEntity.ok(musiclimateService.getPlaylistByCoordinates(latitude, longitude));
	}
}