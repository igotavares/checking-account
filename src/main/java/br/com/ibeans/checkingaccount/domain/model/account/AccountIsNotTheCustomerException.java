package br.com.ibeans.checkingaccount.domain.model.account;

import br.com.ibeans.checkingaccount.domain.model.shared.exception.BusinessRuntimeException;

public class AccountIsNotTheCustomerException extends BusinessRuntimeException {
    public AccountIsNotTheCustomerException() {
        super("The account is not the user");
    }
}
