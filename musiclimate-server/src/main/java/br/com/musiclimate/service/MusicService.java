package br.com.musiclimate.service;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import br.com.musiclimate.client.resource.PlaylistResource;
import br.com.musiclimate.client.resource.TrackResource;
import br.com.musiclimate.client.service.MusicApiIntegrationService;
import br.com.musiclimate.domain.MusicCategory;
import br.com.musiclimate.error.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MusicService {
	
	@Autowired
	private MusicApiIntegrationService musicApiService;

	/**
	 * Method responsible for getting a list of tracks, given a category id. Caching
	 * the result list here makes it easier to get cache hits since there are only
	 * four possible categories
	 * 
	 * @param musicCategory music category id
	 * @return List of music tracks
	 */
	@HystrixCommand(commandKey = "getTracksByCategory", fallbackMethod = "getFallbackPlaylist", commandProperties = @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000"), 
			ignoreExceptions = { ResourceNotFoundException.class, InvalidParameterException.class })
	@Cacheable(value = "tracksByCategory", key = "#musicCategory", unless="#result.size() == 1")
	public List<String> getTracksByMusicCategory(MusicCategory musicCategory) {
		PlaylistResource playlistResource = musicApiService.getPlayListByCategory(musicCategory.getDescription());
		String playlistId = playlistResource.getPlaylists().getItems().get(0).getPlaylistId();

		TrackResource trackResource = musicApiService.getTracksByPlaylist(playlistId);
		return trackResource.getTracks().stream().filter(trackItem -> trackItem.getTrack() != null)
				.map(trackItem -> trackItem.getTrack().getName())
				.collect(Collectors.toList());
	}

	public List<String> getFallbackPlaylist(MusicCategory musicCategory) {
		log.debug("playlist fallback triggered {}", musicCategory);
		return Arrays.asList("Cry me a river");
	}
}
