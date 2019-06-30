package br.com.musiclimate.resource;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class Track implements Serializable {

	private static final long serialVersionUID = -2325059096691100627L;

	@JsonProperty("name")
	private String name;
}
