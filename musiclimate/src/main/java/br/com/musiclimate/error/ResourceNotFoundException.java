package br.com.musiclimate.error;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ResourceNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -3809892005249303769L;

	private HttpStatus status;

	public ResourceNotFoundException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}
}
