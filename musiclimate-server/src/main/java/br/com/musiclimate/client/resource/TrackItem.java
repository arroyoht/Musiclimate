package br.com.musiclimate.client.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TrackItem {

	@JsonProperty("track")
	private Track track;
}
