package br.com.ibeans.checkingaccount.domain.model.transaction;

import br.com.ibeans.checkingaccount.domain.model.shared.exception.BusinessRuntimeException;

public class DailyLimitExceededException extends BusinessRuntimeException {

    public DailyLimitExceededException() {
        super("Daily limit exceeded");
    }
}
