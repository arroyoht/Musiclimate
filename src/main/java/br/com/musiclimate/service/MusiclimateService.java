package br.com.musiclimate.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.musiclimate.domain.MusicCategory;
import br.com.musiclimate.error.ServiceUnavailableException;
import feign.FeignException;

@Service
public class MusiclimateService {
	
	@Autowired
	private MusicService musicService;

	@Autowired
	private TemperatureService temperatureService;

	/**
	 * Method responsible for getting a playlist of songs given latitude and
	 * longitude coordinates
	 * 
	 * @param lat latitude
	 * @param lon longitude
	 * @return List of playlist song names
	 */
	public List<String> getPlaylistByCoordinates(Double lat, Double lon) {
		try {
			Double temp = temperatureService.getTemperatureByCoordinate(lat, lon);
			return musicService.getTracksByMusicCategory(getMusicCategory(temp));
		} catch (FeignException e) {
			throw new ServiceUnavailableException("Service Unavailable");
		}
	}

	/**
	 * Method responsible for getting a playlist of songs given a city name
	 * 
	 * @param cityName
	 * @return List of playlist song names
	 */
	public List<String> getPlaylistByCity(String city) {
		try {
			Double temp = temperatureService.getTemperatureByCity(city);
			return musicService.getTracksByMusicCategory(getMusicCategory(temp));
		} catch (FeignException e) {
			throw new ServiceUnavailableException("Service Unavailable");
		}
	}

	private MusicCategory getMusicCategory(Double temperature) {
		if (temperature.compareTo(14d) > 0) {
			if (temperature.compareTo(30d) > 0) {
				return MusicCategory.PARTY;
			} else {
				return MusicCategory.POP;
			}
		} else {
			if (temperature.compareTo(10d) < 0) {
				return MusicCategory.CLASSICAL;
			} else {
				return MusicCategory.ROCK;
			}
		}
	}
}
