package br.com.ibeans.checkingaccount.application.impl;

import br.com.ibeans.checkingaccount.CheckingAccountConstants.*;
import br.com.ibeans.checkingaccount.domain.model.account.Account;
import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.account.AccountNotFoundException;
import br.com.ibeans.checkingaccount.domain.model.customer.Customer;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerService;
import br.com.ibeans.checkingaccount.domain.model.shared.IdentityGenerator;
import br.com.ibeans.checkingaccount.domain.model.transaction.*;
import br.com.ibeans.checkingaccount.port.adapter.persistence.AccountRepositoy;
import br.com.ibeans.checkingaccount.port.adapter.persistence.FinancialMovementRepository;
import br.com.ibeans.checkingaccount.port.adapter.persistence.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private IdentityGenerator identityMock;
    @Mock
    private CustomerService customerServiceMock;
    @Mock
    private AccountRepositoy accountRepositoyMock;
    @Mock
    private FinancialMovementRepository financialMovementRepositoryMock;
    @Mock
    private TransactionRepository transactionRepositoryMock;
    @Mock
    private NotificationTransactionService notificationTransactionServiceMock;
    @Mock
    private DailyLimitSpec dailyLimitMock;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Nested
    class WhenCreate {

        @BeforeEach
        void context() {
            Account accountFrom = Account.builder()
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .build();

            Account accountTo = Account.builder()
                    .amount(AccountConstants.AMOUNT_IS_20)
                    .build();

            given(accountRepositoyMock.findById(new AccountId(AccountConstants.ID_IS_ONE)))
                    .willReturn(Optional.of(accountFrom));

            lenient().when(accountRepositoyMock.findBy(AgencyConstants.NUMBER_IS_5678,
                    AccountConstants.NUMBER_IS_4321,
                    AccountConstants.DIGIT_IS_2))
                    .thenReturn(Optional.of(accountTo));
        }

        @Test
        void shouldFindTheAccountFrom() {
            Transaction transaction = Transaction.builder()
                    .accountFrom(AccountTransaction.builder()
                            .accountId(new AccountId(AccountConstants.ID_IS_ONE))
                            .build())
                    .build();

            create(transaction);

            verify(accountRepositoyMock).findById(new AccountId(AccountConstants.ID_IS_ONE));
        }

        @Test
        void givenShouldNotFindTheAccountFrom() {
            given(accountRepositoyMock.findById(new AccountId(AccountConstants.ID_IS_ONE)))
                    .willReturn(Optional.empty());

            Transaction transaction = Transaction.builder()
                    .accountFrom(AccountTransaction.builder()
                            .accountId(new AccountId(AccountConstants.ID_IS_ONE))
                            .build())
                    .build();

            try {
                transactionService.create(transaction);
                fail();
            } catch (AccountNotFoundException cause) {
                String expected = "Account not found";

                assertEquals(expected, cause.getMessage());
            }

        }

        @Test
        void givenThatFindTheAccountFromShouldNotFindTheAccountTo() {
            Account accountFrom = Account.builder().build();

            given(accountRepositoyMock.findById(new AccountId(AccountConstants.ID_IS_ONE)))
                    .willReturn(Optional.of(accountFrom));

            given(accountRepositoyMock.findBy(AgencyConstants.NUMBER_IS_5678,
                                              AccountConstants.NUMBER_IS_4321,
                                              AccountConstants.DIGIT_IS_2))
                    .willReturn(Optional.empty());

            Transaction transaction = Transaction.builder()
                    .accountFrom(AccountTransaction.builder()
                            .accountId(new AccountId(AccountConstants.ID_IS_ONE))
                            .build())
                    .accountTo(AccountTransaction.builder()
                            .agency(AgencyConstants.NUMBER_IS_5678)
                            .number(AccountConstants.NUMBER_IS_4321)
                            .digit(AccountConstants.DIGIT_IS_2)
                            .build())
                    .build();

            try {
                transactionService.create(transaction);
                fail();
            } catch (AccountNotFoundException cause) {
                String expected = "Transfer account not found";

                assertEquals(expected, cause.getMessage());
            }
        }

        @Test
        void givenThatFindACustomerShouldNotFindTheCustomer() {
            Customer customer = Customer.builder().build();

            given(customerServiceMock.customerFrom(DocumentConstants.NUMBER_IS_06045672322))
                    .willReturn(customer);

            Transaction transaction = Transaction.builder()
                    .accountFrom(AccountTransaction.builder()
                            .accountId(new AccountId(AccountConstants.ID_IS_ONE))
                            .build())
                    .accountTo(AccountTransaction.builder()
                            .agency(AgencyConstants.NUMBER_IS_5678)
                            .number(AccountConstants.NUMBER_IS_4321)
                            .digit(AccountConstants.DIGIT_IS_2)
                                .customer(CustomerTransaction.builder()
                                    .name(CustomerConstants.NAME_IS_PEDRO_COSTA)
                                    .documentNumber(DocumentConstants.NUMBER_IS_06045672322)
                                    .build())
                            .build())
                    .amount(TransactionConstants.AMOUNT_IS_10)
                    .build();


            transactionService.create(transaction);
        }

        private void create(Transaction transaction) {
            try {
                transactionService.create(transaction);
            } catch (Exception cause) { }
        }

    }

}
