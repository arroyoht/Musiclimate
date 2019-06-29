package br.com.musiclimate.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WeatherResource {

	@JsonProperty("name")
	private String cityName;
	
	@JsonProperty("coord")
	private Coordinate coordinate;
	
	@JsonProperty("main")
	private Weather weather;
}
