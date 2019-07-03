package br.com.musiclimate.client.error;

import java.security.InvalidParameterException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import br.com.musiclimate.error.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

@Component
public class ClientErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        if (response.status() == HttpStatus.NOT_FOUND.value()) {
            return new ResourceNotFoundException("Resource not found", HttpStatus.NOT_FOUND);
        }
        if (response.status() == HttpStatus.BAD_REQUEST.value()) {
            return new InvalidParameterException("Invalid parameters");
        }
        return defaultErrorDecoder.decode(methodKey, response);
    }
}