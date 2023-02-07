package br.com.ibeans.checkingaccount.port.adapter.resource.converter;

import br.com.ibeans.checkingaccount.domain.model.transaction.Transaction;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.TransactionData;

public interface TransactionConverter extends Converter<Transaction, TransactionData>{
}
