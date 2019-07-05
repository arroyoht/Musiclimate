package br.com.musiclimate;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MusiclimateApplicationTests {

	@Value("${spring.profiles.active}")
	String activeProfile;

	@Test
	public void contextLoads() {
		assertTrue(activeProfile.equals("test"));
	}
}