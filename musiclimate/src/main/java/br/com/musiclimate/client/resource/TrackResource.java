package br.com.musiclimate.client.resource;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TrackResource {

	@JsonProperty("items")
	private List<TrackItem> tracks;
}
