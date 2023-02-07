package br.com.ibeans.checkingaccount.port.adapter.resource.converter.impl;

import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.transaction.AccountTransaction;
import br.com.ibeans.checkingaccount.domain.model.transaction.CustomerTransaction;
import br.com.ibeans.checkingaccount.domain.model.transaction.Transaction;
import br.com.ibeans.checkingaccount.port.adapter.resource.converter.TransactionConverter;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.AccountTransactionData;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.CustomerTransactionData;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.TransactionData;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverterImpl implements TransactionConverter {

    @Override
    public TransactionData toData(Transaction transaction) {
        return TransactionData.builder()
                .id(transaction.id())
                .accountFrom(toData(transaction.getAccountFrom()))
                .accountTo(toData(transaction.getAccountTo()))
                .amount(transaction.getAmount())
                .build();
    }

    private AccountTransactionData toData(AccountTransaction account) {
        if (account != null) {
            return AccountTransactionData.builder()
                    .id(account.getId())
                    .accountId(account.accountId())
                    .agency(account.getAgency())
                    .number(account.getNumber())
                    .digit(account.getDigit())
                    .customer(toData(account.getCustomer()))
                    .build();
        }
        return null;
    }

    private CustomerTransactionData toData(CustomerTransaction customer) {
        if (customer != null) {
            return CustomerTransactionData.builder()
                    .documentNumber(customer.getDocumentNumber())
                    .name(customer.getName())
                    .build();
        }
        return null;
    }

    @Override
    public Transaction toEntity(TransactionData transaction) {
        return Transaction.builder()
                .accountFrom(toEntity(transaction.getAccountFrom()))
                .accountTo(toEntity(transaction.getAccountTo()))
                .amount(transaction.getAmount())
                .build();
    }

    private AccountTransaction toEntity(AccountTransactionData account) {
        if (account != null) {
            return AccountTransaction.builder()
                    .id(account.getId())
                    .accountId(createAccountId(account.getAccountId()))
                    .agency(account.getAgency())
                    .number(account.getNumber())
                    .digit(account.getDigit())
                    .customer(toEntity(account.getCustomer()))
                    .build();
        }
        return null;
    }

    private AccountId createAccountId(String accountId) {
        if (accountId != null) {
            return new AccountId(accountId);
        }
        return null;
    }

    private CustomerTransaction toEntity(CustomerTransactionData customer) {
        if (customer != null) {
            return CustomerTransaction.builder()
                    .documentNumber(customer.getDocumentNumber())
                    .name(customer.getName())
                    .build();
        }
        return null;
    }

}
