package br.com.musiclimate.resource;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TrackResource implements Serializable {

	private static final long serialVersionUID = 6888332575208537623L;

	@JsonProperty("items")
	private List<TrackItem> tracks;
}
