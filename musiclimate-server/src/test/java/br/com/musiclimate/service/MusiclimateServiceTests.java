package br.com.musiclimate.service;

import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.musiclimate.domain.MusicCategory;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MusiclimateServiceTests {

	private Double FREEZING_TEMP = 0.0;
	private Double COLD_TEMP = 14.0;
	private Double WARM_TEMP = 23.0;
	private Double HOT_TEMP = 32.0;

	private String musicTrack = "Cry me a river";
	private String city = "Campinas";

	@MockBean
	private MusicService musicService;

	@MockBean
	private TemperatureService temperatureService;
	
	@Autowired
	private MusiclimateService musiclimateService;

	@Test
	public void givenThatItsFreezing_WhenGetPlaylist_ThenReturnClassical() {
		Mockito.when(this.temperatureService.getTemperatureByCity(city)).thenReturn(FREEZING_TEMP);
		Mockito.when(this.musicService.getTracksByMusicCategory(MusicCategory.CLASSICAL))
				.thenReturn(Arrays.asList(musicTrack));
		
		List<String> tracks = musiclimateService.getPlaylistByCity(city);
		
		Mockito.verify(this.musicService).getTracksByMusicCategory(MusicCategory.CLASSICAL);
		assertTrue(tracks.contains(musicTrack));
	}
	
	@Test
	public void givenThatItsCold_WhenGetPlaylist_ThenReturnRock() {
		Mockito.when(this.temperatureService.getTemperatureByCity(city)).thenReturn(COLD_TEMP);
		Mockito.when(this.musicService.getTracksByMusicCategory(MusicCategory.ROCK))
				.thenReturn(Arrays.asList(musicTrack));
		
		List<String> tracks = musiclimateService.getPlaylistByCity(city);
		
		Mockito.verify(this.musicService).getTracksByMusicCategory(MusicCategory.ROCK);
		assertTrue(tracks.contains(musicTrack));
	}
	
	@Test
	public void givenThatItsWarm_WhenGetPlaylist_ThenReturnPop() {
		Mockito.when(this.temperatureService.getTemperatureByCity(city)).thenReturn(WARM_TEMP);
		Mockito.when(this.musicService.getTracksByMusicCategory(MusicCategory.POP))
				.thenReturn(Arrays.asList(musicTrack));
		
		List<String> tracks = musiclimateService.getPlaylistByCity(city);
		
		Mockito.verify(this.musicService).getTracksByMusicCategory(MusicCategory.POP);
		assertTrue(tracks.contains(musicTrack));
	}
	
	@Test
	public void givenThatItsFreezing_WhenGetPlaylist_ThenReturnParty() {	
		Mockito.when(this.temperatureService.getTemperatureByCity(city)).thenReturn(HOT_TEMP);
		Mockito.when(this.musicService.getTracksByMusicCategory(MusicCategory.PARTY))
				.thenReturn(Arrays.asList(musicTrack));
		
		List<String> tracks = musiclimateService.getPlaylistByCity(city);
		Mockito.verify(this.musicService).getTracksByMusicCategory(MusicCategory.PARTY);
		assertTrue(tracks.contains(musicTrack));
	}
}
