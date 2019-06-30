package br.com.musiclimate.service;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.musiclimate.domain.MusicCategory;
import br.com.musiclimate.error.ResourceNotFoundException;
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
	@HystrixCommand(commandKey = "playlistByCoordinates", fallbackMethod = "getFallbackPlaylist", ignoreExceptions = {
			ResourceNotFoundException.class, InvalidParameterException.class })
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
	@HystrixCommand(commandKey = "playlistByCity", fallbackMethod = "getFallbackPlaylist", ignoreExceptions = {
			ResourceNotFoundException.class, InvalidParameterException.class })
	public List<String> getPlaylistByCity(String city) {
		try {
			Double temp = temperatureService.getTemperatureByCity(city);
			return musicService.getTracksByMusicCategory(getMusicCategory(temp));
		} catch (FeignException e) {
			throw new ServiceUnavailableException("Service Unavailable");
		}
	}

	// TODO: Improve fallback strategy
	
	public List<String> getFallbackPlaylist(String city) {
		return Arrays.asList("Beautiful People (feat. Khalid)", "Señorita", "Panini", "Call You Mine (with Bebe Rexha)",
				"On A Roll", "You Need To Calm Down", "Never Really Over", "Only Human",
				"One Thing Right - feat. Kane Brown", "I Don't Care (with Justin Bieber)", "bad guy", "Rodeo",
				"Money In The Grave (Drake ft. Rick Ross)", "Post Malone (feat. RANI)",
				"Summer Days (feat. Macklemore & Patrick Stump of Fall Out Boy)", "Find U Again (feat. Camila Cabello)",
				"QUE PRETENDES", "Higher Love", "Heaven", "Someone You Loved", "It's You", "Mad Love",
				"Don't Check On Me (feat. Justin Bieber & Ink)", "Focus (feat. 21 Savage)",
				"I Think I'm OKAY (with YUNGBLUD & Travis Barker)", "Go Loko", "Old Town Road - Remix", "Rescue Me",
				"All Around The World (La La La)", "The Git Up", "Mother's Daughter", "If I Can't Have You",
				"Cross Me (feat. Chance the Rapper & PnB Rock)", "Loco Contigo (with J. Balvin & Tyga)", "Easier",
				"No Guidance (feat. Drake)", "Sanctuary", "Civil War", "Ritual", "Sad Song (feat. TINI)");
	}

	public List<String> getFallbackPlaylist(Double lat, Double lon) {
		return Arrays.asList("Beautiful People (feat. Khalid)", "Señorita", "Panini", "Call You Mine (with Bebe Rexha)",
				"On A Roll", "You Need To Calm Down", "Never Really Over", "Only Human",
				"One Thing Right - feat. Kane Brown", "I Don't Care (with Justin Bieber)", "bad guy", "Rodeo",
				"Money In The Grave (Drake ft. Rick Ross)", "Post Malone (feat. RANI)",
				"Summer Days (feat. Macklemore & Patrick Stump of Fall Out Boy)", "Find U Again (feat. Camila Cabello)",
				"QUE PRETENDES", "Higher Love", "Heaven", "Someone You Loved", "It's You", "Mad Love",
				"Don't Check On Me (feat. Justin Bieber & Ink)", "Focus (feat. 21 Savage)",
				"I Think I'm OKAY (with YUNGBLUD & Travis Barker)", "Go Loko", "Old Town Road - Remix", "Rescue Me",
				"All Around The World (La La La)", "The Git Up", "Mother's Daughter", "If I Can't Have You",
				"Cross Me (feat. Chance the Rapper & PnB Rock)", "Loco Contigo (with J. Balvin & Tyga)", "Easier",
				"No Guidance (feat. Drake)", "Sanctuary", "Civil War", "Ritual", "Sad Song (feat. TINI)");
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
