package br.com.musiclimate.service;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import br.com.musiclimate.client.service.MusicService;
import br.com.musiclimate.client.service.WeatherService;
import br.com.musiclimate.domain.MusicCategory;
import br.com.musiclimate.error.ResourceNotFoundException;
import br.com.musiclimate.error.ServiceUnavailableException;
import br.com.musiclimate.resource.PlaylistResource;
import br.com.musiclimate.resource.TrackResource;
import br.com.musiclimate.resource.WeatherResource;
import feign.FeignException;

@Service
public class MusiclimateServiceImpl implements MusiclimateService {

	@Autowired
	private MusicService musicService;

	@Autowired
	private WeatherService weatherService;

	/**
	 * Method responsible for getting a playlist of songs given latitude and
	 * longitude coordinates
	 * 
	 * @param latitude
	 * @param longitude
	 * @return List of playlist song names
	 */
	@Override
	public List<String> getPlaylistByCoordinates(Double latitude, Double longitude) {
		try {
			MusicCategory musicCategory = getMusicCategory(getTemperature(latitude, longitude));
			return getTracksByMusicCategory(musicCategory);
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
	@Override
	public List<String> getPlaylistByCity(String cityName) {
		try {
			MusicCategory musicCategory = getMusicCategory(getTemperature(cityName));
			return getTracksByMusicCategory(musicCategory);
		} catch (FeignException e) {
			throw new ServiceUnavailableException("Service Unavailable");
		}
	}

	private Double getTemperature(String city) {
		if (StringUtils.isEmpty(city)) {
			throw new InvalidParameterException("City name is required");
		}

		WeatherResource resource = weatherService.getWeatherByCity(city);
		if (resource.getWeather() == null || resource.getWeather().getTemperature() == null) {
			throw new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND);
		}

		return resource.getWeather().getTemperature();
	}

	private Double getTemperature(Double lat, Double lon) {
		if (lat == null || lon == null) {
			throw new InvalidParameterException("location coordinates is required");
		}

		WeatherResource resource = weatherService.getWeatherByCoordinates(lat, lon);
		if (resource.getWeather() == null || resource.getWeather().getTemperature() == null) {
			throw new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND);
		}

		return resource.getWeather().getTemperature();
	}

	private List<String> getTracksByMusicCategory(MusicCategory musicCategory) {
		String playlistId = getPlaylistIdByMusicCategory(musicCategory);
		TrackResource trackResource = musicService.getTracksByPlaylist(playlistId);

		return trackResource.getTracks().stream().map(trackItem -> trackItem.getTrack().getName())
				.collect(Collectors.toList());
	}

	private String getPlaylistIdByMusicCategory(MusicCategory musicCategory) {
		PlaylistResource playlistResource = musicService.getPlayListByCategory(musicCategory.getDescription());

		return playlistResource.getPlaylists().getItems().get(0).getPlaylistId();
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
