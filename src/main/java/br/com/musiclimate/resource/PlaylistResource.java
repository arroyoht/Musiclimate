package br.com.musiclimate.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlaylistResource implements Serializable {

	private static final long serialVersionUID = 6184825102994574175L;
	
	@JsonProperty("playlists")
	private Playlists playlists;
}