package br.com.musiclimate.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class WeatherResource implements Serializable {

	private static final long serialVersionUID = 1077746044981985857L;

	@JsonProperty("name")
	private String cityName;

	@JsonProperty("coord")
	private Coordinate coordinate;

	@JsonProperty("main")
	private Weather weather;
}