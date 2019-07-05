package br.com.musiclimate.client.resource;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TrackResource {

	@JsonProperty("items")
	private List<TrackItem> tracks;
	
	public List<TrackItem> getTracks(){
		return tracks == null ? new ArrayList<>() : tracks;
	}
}
