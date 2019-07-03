package br.com.musiclimate.error.handler;

import java.security.InvalidParameterException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.musiclimate.error.CustomErrorResponse;
import br.com.musiclimate.error.ResourceNotFoundException;
import br.com.musiclimate.error.ServiceUnavailableException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		return new ResponseEntity<>(buildResponseMessage(ex.getMessage(), ex.getStatus()), ex.getStatus());
	}

	@ExceptionHandler(value = { MethodArgumentTypeMismatchException.class, InvalidParameterException.class })
	public ResponseEntity<Object> handleTypeMismatchException(RuntimeException ex, WebRequest request) {
		String message = "Invalid parameters";
		return new ResponseEntity<>(buildResponseMessage(message, HttpStatus.BAD_REQUEST), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ServiceUnavailableException.class)
	public ResponseEntity<Object> handleServiceUnavailableExceptionException(RuntimeException ex, WebRequest request) {
		return new ResponseEntity<>(buildResponseMessage(ex.getMessage(), HttpStatus.SERVICE_UNAVAILABLE),
				HttpStatus.SERVICE_UNAVAILABLE);
	}

	private CustomErrorResponse buildResponseMessage(String message, HttpStatus status) {
		CustomErrorResponse errorMessage = new CustomErrorResponse();
		errorMessage.setTimestamp(LocalDateTime.now());
		errorMessage.setError(message);
		errorMessage.setStatus(status.value());

		return errorMessage;
	}
}