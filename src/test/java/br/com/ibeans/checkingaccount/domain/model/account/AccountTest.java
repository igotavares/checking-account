package br.com.ibeans.checkingaccount.domain.model.account;

import br.com.ibeans.checkingaccount.CheckingAccountConstants.*;
import br.com.ibeans.checkingaccount.CheckingAccountConstants.CustomerConstants;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class AccountTest {

    @Nested
    class WhenValidCustomer {

        @Test
        void shouldBeValid() {
            Account account = createAccount(CustomerConstants.ID_IS_ONE);
            account.validCustomer(new CustomerId(CustomerConstants.ID_IS_ONE));
        }

        @Test
        void shouldNotBeValid() {
            Account account = createAccount(CustomerConstants.ID_IS_ONE);

            try {
                account.validCustomer(new CustomerId(CustomerConstants.ID_IS_TWO));
                fail();
            } catch (AccountIsNotTheCustomerException cause) {
                String expected = "The account is not the user";

                assertEquals(expected, cause.getMessage());
            }
        }
    }

    @Nested
    class WhenDebit {

        @Test
        void givenAmountIsTwoWhenDebitOneShouldOne() {
            Account account = new Account();
            account.setAmount(BigDecimal.valueOf(2D));
            account.debit(BigDecimal.ONE);

            BigDecimal expected = BigDecimal.ONE.setScale(1);

            assertEquals(expected, account.getAmount());
        }
        @Test
        void givenAmountIsOneWhenDebitOneShouldZero() {
            Account account = new Account();
            account.setAmount(BigDecimal.ONE);
            account.debit(BigDecimal.ONE);

            BigDecimal expected = BigDecimal.ZERO;

            assertEquals(expected, account.getAmount());
        }

        @Test
        void givenAmountIsOnWhenDebitTwoShouldThrowInsufficientBalance() {
            Account account = new Account();
            account.setAmount(BigDecimal.ONE);
            try {
                account.debit(BigDecimal.valueOf(2D));
                fail();
            } catch (InsufficientBalanceException cause) {
                String expected = "Insufficient Balance";
                assertEquals(expected, cause.getMessage());
            }
        }

    }
    @Nested
    class WhenCreateDebitMovement {

        @Test
        public void shouldCreateDebitMovement() {
            Account account = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .build();
            FinancialMovement actual = account.createDebitMovement(FinancialMovementConstants.ID_IS_ONE,
                    FinancialMovementConstants.AMOUNT_IS_10,
                    new AccountId(AccountConstants.ID_IS_TWO));

            String expected = "FinancialMovement(from=AccountId(id=4fb6d970-5135-4168-ae7f-b4011d15e13f), to=AccountId(id=e1fa20fa-c241-42fe-8401-61f0f44c7e18), type=DEBIT, amount=10)";

            assertEquals(expected, actual.toString());
        }
    }

    @Nested
    class WhenCreateCreditMovement {

        @Test
        public void shouldCreateDebitMovement() {
            Account account = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .build();
            FinancialMovement actual = account.createCreditMovement(FinancialMovementConstants.ID_IS_ONE,
                    FinancialMovementConstants.AMOUNT_IS_10,
                    new AccountId(AccountConstants.ID_IS_TWO));

            String expected = "FinancialMovement(from=AccountId(id=e1fa20fa-c241-42fe-8401-61f0f44c7e18), to=AccountId(id=4fb6d970-5135-4168-ae7f-b4011d15e13f), type=CREDIT, amount=10)";

            assertEquals(expected, actual.toString());
        }
    }

    protected Account createAccount(String customerId) {
        Account account = new Account();
        account.setCustomerId(new CustomerId(customerId));
        return account;
    }

}