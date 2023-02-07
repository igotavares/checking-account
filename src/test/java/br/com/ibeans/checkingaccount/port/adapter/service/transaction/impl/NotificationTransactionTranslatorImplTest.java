package br.com.ibeans.checkingaccount.port.adapter.service.transaction.impl;

import br.com.ibeans.checkingaccount.CheckingAccountConstants;
import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.customer.DocumentType;
import br.com.ibeans.checkingaccount.domain.model.transaction.AccountTransaction;
import br.com.ibeans.checkingaccount.domain.model.transaction.CustomerTransaction;
import br.com.ibeans.checkingaccount.domain.model.transaction.Transaction;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NotificationTransactionTranslatorImplTest {

    NotificationTransactionTranslatorImpl notificationTransactionTranslator;

    public NotificationTransactionTranslatorImplTest() {
        this.notificationTransactionTranslator = new NotificationTransactionTranslatorImpl();
    }

    @Nested
    class WhenFrom {
        @Test
        void should() {
            Transaction transaction = Transaction.builder()
                    .accountFrom(AccountTransaction.builder()
                            .accountId(new AccountId(CheckingAccountConstants.AccountConstants.ID_IS_ONE))
                            .build())
                    .accountTo(AccountTransaction.builder()
                            .agency(CheckingAccountConstants.AgencyConstants.NUMBER_IS_5678)
                            .number(CheckingAccountConstants.AccountConstants.NUMBER_IS_4321)
                            .digit(CheckingAccountConstants.AccountConstants.DIGIT_IS_2)
                            .customer(CustomerTransaction.builder()
                                    .name(CheckingAccountConstants.CustomerConstants.NAME_IS_PEDRO_COSTA)
                                    .documentNumber(CheckingAccountConstants.DocumentConstants.NUMBER_IS_06045672322)
                                    .build())
                            .build())
                    .amount(CheckingAccountConstants.TransactionConstants.AMOUNT_IS_10)
                    .build();

            String actual = notificationTransactionTranslator.from(transaction);

            String expected = "{\"documentNumberTo\":\"06045672322\",\"amount\":10,\"agency\":5678,\"nameTo\":\"Pedro Costa\"}";

            assertEquals(expected, actual);
        }
    }

}