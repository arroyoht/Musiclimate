package br.com.musiclimate.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Weather implements Serializable {

	private static final long serialVersionUID = -3360350089874984908L;

	@JsonProperty("temp")
	private Double temperature;

	@JsonProperty("temp_min")
	private Double maxTemperature;

	@JsonProperty("temp_max")
	private Double minTemperature;
}
