package br.com.ibeans.checkingaccount.port.adapter.persistence;

import br.com.ibeans.checkingaccount.domain.model.transaction.Transaction;
import br.com.ibeans.checkingaccount.domain.model.transaction.TransactionId;
import org.springframework.data.repository.CrudRepository;

public interface TransactionRepository extends CrudRepository<Transaction, TransactionId> {
}
