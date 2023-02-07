package br.com.ibeans.checkingaccount.port.adapter.service.transaction.impl;

import br.com.ibeans.checkingaccount.domain.model.customer.CustomerException;
import br.com.ibeans.checkingaccount.domain.model.transaction.NotificationTransactionService;
import br.com.ibeans.checkingaccount.domain.model.transaction.Transaction;
import br.com.ibeans.checkingaccount.port.adapter.service.transaction.NotificationTransactionAdapter;
import br.com.ibeans.checkingaccount.port.adapter.service.transaction.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpNotificationTransactionAdapterImpl implements NotificationTransactionAdapter {

    private static Logger log = LoggerFactory.getLogger(HttpNotificationTransactionAdapterImpl.class);

    @Value("${service.notification.transaction.uri}")
    private String uri;

    private RestTemplate restTemplate;
    private Translator<Transaction> transactionTranslator;

    public HttpNotificationTransactionAdapterImpl(RestTemplate restTemplate, Translator<Transaction> transactionTranslator) {
        this.restTemplate = restTemplate;
        this.transactionTranslator = transactionTranslator;
    }

    @Override
    public void toTransaction(Transaction transaction) {
        try {
            String body = transactionTranslator.from(transaction);
            restTemplate.postForObject(uri, body, String.class);
        } catch (HttpStatusCodeException cause) {
            log.error(new StringBuilder("Unable to Search Customer\n Response:")
                    .append("\n   status: ").append(cause.getStatusCode().value())
                    .append("\n   body: ").append(cause.getResponseBodyAsString())
                    .toString());
            throw new CustomerException("Unable to Search Customer");
        }
    }
}
