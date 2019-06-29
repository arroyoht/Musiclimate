package br.com.musiclimate.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.musiclimate.client.service.MusicService;
import br.com.musiclimate.resource.PlaylistResource;
import br.com.musiclimate.resource.TrackResource;

@RestController
@RequestMapping(value = "/music", produces = MediaType.APPLICATION_JSON_VALUE)
public class MusicController {

	@Autowired
	MusicService musicService;

	@GetMapping("/category/{category}")
	public ResponseEntity<PlaylistResource> getPlaylistByCategory(@PathVariable("category") String category) {
		return ResponseEntity.ok(musicService.getPlayListByCategory(category));
	}

	@GetMapping("/playlist/{playlist_id}")
	public ResponseEntity<TrackResource> getTracksByPlaylist(@PathVariable("playlist_id") String playlistId) {
		return ResponseEntity.ok(musicService.getTracksByPlaylist(playlistId));
	}
}