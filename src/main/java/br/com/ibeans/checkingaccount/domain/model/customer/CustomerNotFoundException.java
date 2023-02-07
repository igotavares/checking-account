package br.com.ibeans.checkingaccount.domain.model.customer;

import br.com.ibeans.checkingaccount.domain.model.shared.exception.BusinessRuntimeException;

public class CustomerNotFoundException extends BusinessRuntimeException {

    public CustomerNotFoundException() {
        super("Customer not found!");
    }

}
