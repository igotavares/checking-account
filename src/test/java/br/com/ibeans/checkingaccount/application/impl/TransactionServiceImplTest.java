package br.com.ibeans.checkingaccount.application.impl;

import br.com.ibeans.checkingaccount.CheckingAccountConstants.*;
import br.com.ibeans.checkingaccount.domain.model.account.*;
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
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .build();

            lenient().when(accountRepositoyMock.findById(new AccountId(AccountConstants.ID_IS_ONE)))
                    .thenReturn(Optional.of(accountFrom));

            Account accountTo = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_TWO))
                    .amount(AccountConstants.AMOUNT_IS_20)
                    .build();

            lenient().when(accountRepositoyMock.findBy(AgencyConstants.NUMBER_IS_5678,
                    AccountConstants.NUMBER_IS_4321,
                    AccountConstants.DIGIT_IS_2))
                    .thenReturn(Optional.of(accountTo));

            Customer customer = Customer.builder().build();

            lenient().when(customerServiceMock.customerFrom(DocumentConstants.NUMBER_IS_06045672322))
                    .thenReturn(customer);
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
        void givenAccountFromNotFoundShouldThrowAccountNotFound() {
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
        void givenThatFindTheAccountFromShouldFindTheAccountTo() {
            Account accountFromFound = Account.builder().build();

            given(accountRepositoyMock.findById(new AccountId(AccountConstants.ID_IS_ONE)))
                    .willReturn(Optional.of(accountFromFound));

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

            create(transaction);

            verify(accountRepositoyMock).findBy(AgencyConstants.NUMBER_IS_5678, AccountConstants.NUMBER_IS_4321, AccountConstants.DIGIT_IS_2);
        }

        @Test
        void givenNotFoundAccountToShouldThrowTransferAccountNotFound() {


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
        void givenFoundAccountToShouldFindCustomer() {
            Account accountTo = Account.builder()
                    .amount(AccountConstants.AMOUNT_IS_20)
                    .build();

            given(accountRepositoyMock.findBy(AgencyConstants.NUMBER_IS_5678,
                    AccountConstants.NUMBER_IS_4321,
                    AccountConstants.DIGIT_IS_2))
                    .willReturn(Optional.of(accountTo));

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

            create(transaction);

            verify(customerServiceMock)
                    .customerFrom(DocumentConstants.NUMBER_IS_06045672322);
        }

        @Test
        void givenFoundCustomerShouldCheckDailyLimte() {
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

            create(transaction);

            verify(dailyLimitMock)
                    .exceeded(new AccountId(AccountConstants.ID_IS_ONE), TransactionConstants.AMOUNT_IS_10);
        }

        @Test
        void givenDailyLimitCheckedShouldSaveTheDebitedAccount() {
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

            create(transaction);

            Account accountFrom = Account.builder()
                    .amount(AccountConstants.AMOUNT_IS_0)
                    .build();

            verify(accountRepositoyMock).save(accountFrom);
        }

        @Test
        void givenSavedDebitedAccountShouldSaveDevitedMovement() {
            given(identityMock.next()).willReturn(FinancialMovementConstants.ID_IS_ONE);

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

            create(transaction);

            FinancialMovement financialMovement = FinancialMovement.builder()
                    .id(new FinancialMovementId(FinancialMovementConstants.ID_IS_ONE))
                    .from(new AccountId(AccountConstants.ID_IS_ONE))
                    .to(new AccountId(AccountConstants.ID_IS_TWO))
                    .type(FinancialMovementType.DEBIT)
                    .amount(FinancialMovementConstants.AMOUNT_IS_10)
                    .build();

            verify(financialMovementRepositoryMock).save(financialMovement);
        }

        @Test
        void givenSavedDevitedMovementShouldSaveCreditedAccount() {
            given(identityMock.next()).willReturn(FinancialMovementConstants.ID_IS_ONE);

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

            create(transaction);

            Account accountTo = Account.builder()
                    .amount(AccountConstants.AMOUNT_IS_30)
                    .build();

            verify(accountRepositoyMock).save(accountTo);
        }

        @Test
        void givenSavedCreditedAccountShouldSaveCreditedMovement() {
            given(identityMock.next()).willReturn(FinancialMovementConstants.ID_IS_ONE);
            given(identityMock.next()).willReturn(FinancialMovementConstants.ID_IS_ONE);

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

            create(transaction);

            FinancialMovement financialMovement = FinancialMovement.builder()
                    .id(new FinancialMovementId(FinancialMovementConstants.ID_IS_ONE))
                    .from(new AccountId(AccountConstants.ID_IS_TWO))
                    .to(new AccountId(AccountConstants.ID_IS_ONE))
                    .type(FinancialMovementType.CREDIT)
                    .amount(FinancialMovementConstants.AMOUNT_IS_10)
                    .build();

            verify(financialMovementRepositoryMock).save(financialMovement);
        }

        @Test
        void givenSavedCreditedMovementShouldSaveTransaction() {
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

            create(transaction);

            Transaction expected = Transaction.builder()
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

            verify(transactionRepositoryMock).save(expected);
        }

        @Test
        void givenSavedTransactionShouldNotifyTransaction() {
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

            create(transaction);

            Transaction expected = Transaction.builder()
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

            verify(notificationTransactionServiceMock).notify(expected);
        }

        @Test
        void givenNotifiedTransactionShouldReturnSavedTransaction() {
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

            Transaction actual = create(transaction);

            String exptected = "Transaction(accountFrom=null, accountTo=null, amount=null)";

            assertEquals(exptected, actual.toString());
        }

        private Transaction create(Transaction transaction) {
            try {
                return transactionService.create(transaction);
            } catch (Exception cause) {
                return null;
            }
        }

    }

}
