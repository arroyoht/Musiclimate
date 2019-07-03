package br.com.musiclimate.client.resource;

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
	
	public Double getTemperature() {
		if(weather != null) {
			return weather.getTemperature();
		}
		return null;
	}
}