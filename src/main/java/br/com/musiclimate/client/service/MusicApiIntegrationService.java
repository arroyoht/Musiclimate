package br.com.musiclimate.client.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.musiclimate.client.configuration.MusicServiceConfiguration;
import br.com.musiclimate.resource.PlaylistResource;
import br.com.musiclimate.resource.TrackResource;

@FeignClient(name = "musicAPI", url = "${api.music.url}", configuration = MusicServiceConfiguration.class)
public interface MusicApiIntegrationService {

	/**
	 * Method gets music playlists according to given category
	 * 
	 * @param category name
	 * @return playlist resource with list of playlist ids
	 */
	@GetMapping("/browse/categories/{categoryId}/playlists")
	@Cacheable(value = "playlists", key = "#category")
	PlaylistResource getPlayListByCategory(@PathVariable("categoryId") String category);

	/**
	 * Method gets a list of tracks given a playlist id
	 * 
	 * @param playlist id
	 * @return list of tracks identified by the given playlist id
	 */
	@GetMapping("/playlists/{playlistId}/tracks")
	@Cacheable(value = "tracks", key = "#playlist")
	TrackResource getTracksByPlaylist(@PathVariable("playlistId") String playlist);
}