package br.com.ibeans.checkingaccount.domain.model.account;

import br.com.ibeans.checkingaccount.domain.model.shared.exception.BusinessRuntimeException;

public class InsufficientBalanceException extends BusinessRuntimeException {

    public InsufficientBalanceException() {
        super("Insufficient Balance");
    }
}
