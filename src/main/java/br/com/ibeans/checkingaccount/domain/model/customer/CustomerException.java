package br.com.ibeans.checkingaccount.domain.model.customer;

import br.com.ibeans.checkingaccount.domain.model.shared.exception.BusinessRuntimeException;

public class CustomerException extends BusinessRuntimeException {
    public CustomerException(String message) {
        super(message);
    }
}
