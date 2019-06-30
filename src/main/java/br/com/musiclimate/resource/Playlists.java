package br.com.musiclimate.resource;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Playlists implements Serializable {

	private static final long serialVersionUID = -7682390561183359907L;

	@JsonProperty("items")
	private List<PlaylistItem> items;
}
