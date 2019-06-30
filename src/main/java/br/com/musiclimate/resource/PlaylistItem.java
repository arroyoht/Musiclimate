package br.com.musiclimate.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class PlaylistItem implements Serializable {

	private static final long serialVersionUID = -160273258290844116L;
	
	@JsonProperty("id")
	private String playlistId;
}
