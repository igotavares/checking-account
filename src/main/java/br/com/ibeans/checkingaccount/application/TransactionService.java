package br.com.ibeans.checkingaccount.application;

import br.com.ibeans.checkingaccount.domain.model.transaction.Transaction;

public interface TransactionService {

    Transaction create(Transaction transaction);

}
