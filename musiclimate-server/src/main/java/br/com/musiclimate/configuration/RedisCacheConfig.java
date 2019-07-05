package br.com.musiclimate.configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableConfigurationProperties(CacheConfigurationProperties.class)
@Profile({ "local", "docker" })
public class RedisCacheConfig {

	@Value("${spring.redis.host}")
	private String hostName;

	@Value("${spring.redis.port}")
	private Integer port;
	
	@Value("${cache.timeout}")
	private Long timeoutSeconds;
	
	private static RedisCacheConfiguration createCacheConfiguration(long timeoutInSeconds) {
		return RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofSeconds(timeoutInSeconds))
				.disableCachingNullValues();
	}

	@Bean
	public LettuceConnectionFactory redisConnectionFactory() {
		return new LettuceConnectionFactory(new RedisStandaloneConfiguration(hostName, port));
	}

	@Bean
	public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory cf) {
		RedisTemplate<String, String> redisTemplate = new RedisTemplate<String, String>();
		redisTemplate.setConnectionFactory(cf);
		return redisTemplate;
	}

	@Bean
	public RedisCacheConfiguration cacheConfiguration(CacheConfigurationProperties properties) {
		return createCacheConfiguration(timeoutSeconds);
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory,
			CacheConfigurationProperties properties) {
		Map<String, RedisCacheConfiguration> cacheConfigurations = new HashMap<>();

		for (Entry<String, Long> cacheNameAndTimeout : properties.getCacheExpirations().entrySet()) {
			cacheConfigurations.put(cacheNameAndTimeout.getKey(),
					createCacheConfiguration(cacheNameAndTimeout.getValue()));
		}

		return RedisCacheManager.builder(redisConnectionFactory).cacheDefaults(cacheConfiguration(properties))
				.withInitialCacheConfigurations(cacheConfigurations).build();
	}
}