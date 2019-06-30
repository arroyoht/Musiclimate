package br.com.musiclimate.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import br.com.musiclimate.client.resource.PlaylistResource;
import br.com.musiclimate.client.resource.TrackResource;
import br.com.musiclimate.client.service.MusicApiIntegrationService;
import br.com.musiclimate.domain.MusicCategory;

@Service
public class MusicService {

	@Autowired
	private MusicApiIntegrationService musicApiService;

	/**
	 * Method responsible for getting a list of tracks, given a category id
	 * Caching the result list here makes it easier to get cache hits since there are only four possible categories
	 * 
	 * @param musicCategory music category id
	 * @return List of music tracks
	 */
	@Cacheable(value = "tracksByCategory", key = "#musicCategory", unless = "#result == null")
	public List<String> getTracksByMusicCategory(MusicCategory musicCategory) {
		PlaylistResource playlistResource = musicApiService.getPlayListByCategory(musicCategory.getDescription());
		String playlistId = playlistResource.getPlaylists().getItems().get(0).getPlaylistId();
		
		TrackResource trackResource = musicApiService.getTracksByPlaylist(playlistId);
		return trackResource.getTracks().stream().map(trackItem -> trackItem.getTrack().getName())
				.collect(Collectors.toList());
	}

}
