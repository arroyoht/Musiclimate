package br.com.musiclimate.error;

public class ServiceUnavailableException extends RuntimeException {
	
	private static final long serialVersionUID = -3809892005249303769L;

	public ServiceUnavailableException(String message) {
		super(message);
	}
}
