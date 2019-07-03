package br.com.musiclimate.client.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlaylistResource {

	@JsonProperty("playlists")
	private Playlists playlists;
}