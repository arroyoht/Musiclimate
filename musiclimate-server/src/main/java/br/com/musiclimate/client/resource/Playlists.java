package br.com.musiclimate.client.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Playlists {

	@JsonProperty("items")
	private List<PlaylistItem> items;
}
