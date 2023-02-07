package br.com.ibeans.checkingaccount.port.adapter.service.transaction.impl;

import br.com.ibeans.checkingaccount.domain.model.transaction.AccountTransaction;
import br.com.ibeans.checkingaccount.domain.model.transaction.CustomerTransaction;
import br.com.ibeans.checkingaccount.domain.model.transaction.Transaction;
import br.com.ibeans.checkingaccount.port.adapter.service.transaction.Translator;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NotificationTransactionTranslatorImpl implements Translator<Transaction> {

    @Override
    public String from(Transaction transaction) {
        JSONObject json = new JSONObject();
        Optional<AccountTransaction> accountTo = transaction.accountTo();
        Optional<CustomerTransaction> customerTo = accountTo.map(AccountTransaction::getCustomer);
        json.put("agency", accountTo.map(AccountTransaction::getAgency)
                .orElse(null));
        json.put("nameTo", customerTo
                .map(CustomerTransaction::getName)
                .orElse(null));
        json.put("documentNumberTo", customerTo
                .map(CustomerTransaction::getDocumentNumber)
                .orElse(null));
        json.put("amount", transaction.getAmount());
        return json.toString();
    }

}
