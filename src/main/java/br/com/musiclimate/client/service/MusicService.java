package br.com.musiclimate.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.musiclimate.client.configuration.MusicServiceConfiguration;
import br.com.musiclimate.resource.PlaylistResource;
import br.com.musiclimate.resource.TrackResource;

@FeignClient(name = "musicAPI", url = "${api.music.url}", configuration = MusicServiceConfiguration.class)
public interface MusicService {

	/**
	 * Method gets music playlists according to given category
	 * 
	 * @param category name
	 * @return playlist resource with list of playlist ids
	 */
	@GetMapping("/browse/categories/{category_id}/playlists")
	PlaylistResource getPlayListByCategory(@PathVariable("category_id") String category);

	/**
	 * Method gets a list of tracks given a playlist id
	 * 
	 * @param playlist id
	 * @return list of tracks identified by the given playlist id
	 */
	@GetMapping("/playlists/{playlist_id}/tracks")
	TrackResource getTracksByPlaylist(@PathVariable("playlist_id") String playlist);
}