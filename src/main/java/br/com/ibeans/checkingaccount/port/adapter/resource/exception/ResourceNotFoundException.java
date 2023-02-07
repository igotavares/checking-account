package br.com.ibeans.checkingaccount.port.adapter.resource.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public static ResourceNotFoundException create(Exception cause) {
        return new ResourceNotFoundException(cause.getMessage());
    }

}
