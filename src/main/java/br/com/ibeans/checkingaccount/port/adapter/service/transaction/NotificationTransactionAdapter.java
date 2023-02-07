package br.com.ibeans.checkingaccount.port.adapter.service.transaction;

import br.com.ibeans.checkingaccount.domain.model.transaction.Transaction;

public interface NotificationTransactionAdapter {

    void toTransaction(Transaction transaction);

}
