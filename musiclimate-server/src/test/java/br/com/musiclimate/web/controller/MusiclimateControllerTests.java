package br.com.musiclimate.web.controller;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MusiclimateControllerTests {

	private static String ENDPOINT = "musiclimate/";
	
	@LocalServerPort
	private int port;

	private TestRestTemplate restTemplate = new TestRestTemplate();

	@Test
	public void testGetPlaylistByCity() throws Exception {
		ResponseEntity<String[]> response = getByCity("Campinas");
		String[] playlist = response.getBody();

		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertTrue(playlist.length > 0);
	}

	@Test
	public void testGetPlaylistByInvalidCity() throws Exception {
		ResponseEntity<String[]> response = getByCity("Inexistent_City");
		assertTrue(response.getStatusCode().equals(HttpStatus.NOT_FOUND));
	}
	
	@Test
	public void testGetPlaylistByCoordinates() throws Exception {
		ResponseEntity<String[]> response = getByCoordinates("23.5", "43.1");
		String[] playlist = response.getBody();

		assertTrue(response.getStatusCode().equals(HttpStatus.OK));
		assertTrue(playlist.length > 0);
	}

	@Test
	public void testGetPlaylistByInvalidCoordinates() throws Exception {
		ResponseEntity<String[]> response;
		
		// Empty lat
		response = getByCoordinates("", "43.1");
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		
		// Empty lon
		response = getByCoordinates("23.5", "");
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		
		// Invalid lat
		response = getByCoordinates("lat", "43.1");
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
		
		// Invalid lon
		response = getByCoordinates("23.5", "lon");
		assertTrue(response.getStatusCode().equals(HttpStatus.BAD_REQUEST));
	}

	private ResponseEntity<String[]> getByCity(String city) {
		HttpEntity<String> entity = new HttpEntity<String>(null, new HttpHeaders());
		return restTemplate.exchange(createURLWithPort(ENDPOINT, "city/" + city), HttpMethod.GET, entity,
				String[].class);
	}

	private ResponseEntity<String[]> getByCoordinates(String lat, String lon) {
		HttpEntity<String> entity = new HttpEntity<String>(null, new HttpHeaders());
		return restTemplate.exchange(createURLWithPort(ENDPOINT, "coordinates?lat=" + lat + "&lon=" + lon),
				HttpMethod.GET, entity, String[].class);
	}

	private String createURLWithPort(String endpoint, String uri) {
		return "http://localhost:" + port + "/" + endpoint + uri;
	}
}
