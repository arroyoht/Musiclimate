package br.com.musiclimate.configuration;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "cache")
public class CacheConfigurationProperties {
	private Map<String, Long> cacheExpirations = new HashMap<>();
}