package br.com.ibeans.checkingaccount.port.adapter.service.transaction.impl;

import br.com.ibeans.checkingaccount.domain.model.transaction.NotificationTransactionService;
import br.com.ibeans.checkingaccount.domain.model.transaction.Transaction;
import br.com.ibeans.checkingaccount.port.adapter.service.transaction.NotificationTransactionAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TranslatingNotificationTransactionServiceImpl implements NotificationTransactionService {

    private NotificationTransactionAdapter notificationTransactionAdapter;

    @Override
    public void notify(Transaction transaction) {
        notificationTransactionAdapter.toTransaction(transaction);
    }

}
