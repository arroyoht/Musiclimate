package br.com.musiclimate.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TrackResource {

	@JsonProperty("items")
	private List<TrackItem> tracks;
}
