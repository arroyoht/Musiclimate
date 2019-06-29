package br.com.musiclimate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class MusiclimateApplication {
	public static void main(String[] args) {
		SpringApplication.run(MusiclimateApplication.class, args);
	}
}
