package br.com.musiclimate.client.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Coordinate {

	@JsonProperty("lon")
	private Double longitude;

	@JsonProperty("lat")
	private Double latitude;
}
