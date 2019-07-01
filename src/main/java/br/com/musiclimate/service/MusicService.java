package br.com.musiclimate.service;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

import br.com.musiclimate.client.resource.PlaylistResource;
import br.com.musiclimate.client.resource.TrackResource;
import br.com.musiclimate.client.service.MusicApiIntegrationService;
import br.com.musiclimate.domain.MusicCategory;
import br.com.musiclimate.error.ResourceNotFoundException;

@Service
public class MusicService {

	@Autowired
	private MusicApiIntegrationService musicApiService;

	/**
	 * Method responsible for getting a list of tracks, given a category id. Caching
	 * the result list here makes it easier to get cache hits since there are only
	 * four possible categories
	 * 
	 * @param musicCategory music category id
	 * @return List of music tracks
	 */
	@Cacheable(value = "tracksByCategory", key = "#musicCategory")
	@HystrixCommand(commandKey = "getTracksByCategory", fallbackMethod = "getFallbackPlaylist", ignoreExceptions = {
			ResourceNotFoundException.class, InvalidParameterException.class })
	public List<String> getTracksByMusicCategory(MusicCategory musicCategory) {
		PlaylistResource playlistResource = musicApiService.getPlayListByCategory(musicCategory.getDescription());
		String playlistId = playlistResource.getPlaylists().getItems().get(0).getPlaylistId();

		TrackResource trackResource = musicApiService.getTracksByPlaylist(playlistId);
		return trackResource.getTracks().stream().map(trackItem -> trackItem.getTrack().getName())
				.collect(Collectors.toList());
	}
	
	public List<String> getFallbackPlaylist(MusicCategory musicCategory) {
		return Arrays.asList("Beautiful People (feat. Khalid)", "Señorita", "Panini", "Call You Mine (with Bebe Rexha)",
				"On A Roll", "You Need To Calm Down", "Never Really Over", "Only Human",
				"One Thing Right - feat. Kane Brown", "I Don't Care (with Justin Bieber)", "bad guy", "Rodeo",
				"Money In The Grave (Drake ft. Rick Ross)", "Post Malone (feat. RANI)",
				"Summer Days (feat. Macklemore & Patrick Stump of Fall Out Boy)", "Find U Again (feat. Camila Cabello)",
				"QUE PRETENDES", "Higher Love", "Heaven", "Someone You Loved", "It's You", "Mad Love",
				"Don't Check On Me (feat. Justin Bieber & Ink)", "Focus (feat. 21 Savage)",
				"I Think I'm OKAY (with YUNGBLUD & Travis Barker)", "Go Loko", "Old Town Road - Remix", "Rescue Me",
				"All Around The World (La La La)", "The Git Up", "Mother's Daughter", "If I Can't Have You",
				"Cross Me (feat. Chance the Rapper & PnB Rock)", "Loco Contigo (with J. Balvin & Tyga)", "Easier",
				"No Guidance (feat. Drake)", "Sanctuary", "Civil War", "Ritual", "Sad Song (feat. TINI)");
	}
}
