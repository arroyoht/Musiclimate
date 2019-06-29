package br.com.musiclimate.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Weather {

	@JsonProperty("temp")
	private Double temperature;

	@JsonProperty("temp_min")
	private Double maxTemperature;

	@JsonProperty("temp_max")
	private Double minTemperature;
}
