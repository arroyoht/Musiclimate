package br.com.musiclimate.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlaylistItem {

	@JsonProperty("id")
	private String playlistId;
}
