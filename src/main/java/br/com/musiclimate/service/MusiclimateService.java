package br.com.musiclimate.service;

import java.util.List;

public interface MusiclimateService {

	List<String> getPlaylistByCity(String cityName);

	List<String> getPlaylistByCoordinates(Double latitude, Double longitude);
}
