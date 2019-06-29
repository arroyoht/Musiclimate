package br.com.musiclimate.resource;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Track {

	@JsonProperty("name")
	private String name;
}
