package br.com.ibeans.checkingaccount.domain.model.shared.exception;

public class BusinessRuntimeException extends RuntimeException {

    public BusinessRuntimeException() {
    }

    public BusinessRuntimeException(String message) {
        super(message);
    }

}
