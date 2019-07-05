package br.com.musiclimate.client.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.security.oauth2.client.feign.OAuth2FeignRequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;

import feign.RequestInterceptor;

public class MusicServiceConfiguration {

	@Value("${api.music.auth.url}")
	private String accessTokenUri;

	@Value("${api.music.auth.client.id}")
	private String clientId;

	@Value("${api.music.auth.client.secret}")
	private String clientSecret;

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new OAuth2FeignRequestInterceptor(new DefaultOAuth2ClientContext(), resource());
	}

	public OAuth2ProtectedResourceDetails resource() {
		ClientCredentialsResourceDetails details = new ClientCredentialsResourceDetails();
		details.setAccessTokenUri(accessTokenUri);
		details.setClientId(clientId);
		details.setClientSecret(clientSecret);
		return details;
	}
}