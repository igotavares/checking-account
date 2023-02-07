package br.com.ibeans.checkingaccount.domain.model.account;

import br.com.ibeans.checkingaccount.domain.model.shared.exception.BusinessRuntimeException;

public class AccountNotFoundException extends BusinessRuntimeException {

    public AccountNotFoundException() {
        super("Account not found!");
    }

    public AccountNotFoundException(String error) {
        super(error);
    }


}
