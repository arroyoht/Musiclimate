package br.com.musiclimate.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Coordinate implements Serializable {

	private static final long serialVersionUID = -7221730235115087086L;

	@JsonProperty("lon")
	private Double longitude;

	@JsonProperty("lat")
	private Double latitude;
}
