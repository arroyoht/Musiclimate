package br.com.musiclimate.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TrackItem implements Serializable {

	private static final long serialVersionUID = -1456677332897807690L;
	
	@JsonProperty("track")
	private Track track;
}
