package br.com.ibeans.checkingaccount.application.impl;

import br.com.ibeans.checkingaccount.CheckingAccountConstants;
import br.com.ibeans.checkingaccount.CheckingAccountConstants.*;
import br.com.ibeans.checkingaccount.domain.model.account.Account;
import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.account.AccountNotFoundException;
import br.com.ibeans.checkingaccount.domain.model.agency.AgencyId;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerId;
import br.com.ibeans.checkingaccount.domain.model.shared.IdentityGenerator;
import br.com.ibeans.checkingaccount.port.adapter.persistence.AccountRepositoy;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

    @InjectMocks
    AccountServiceImpl accountService;

    @Mock
    AccountRepositoy accountRepositoyMock;

    @Mock
    IdentityGenerator identityGeneratorMock;

    @Nested
    class WhenFindBy {
        @Test
        void shouldFindAccount() {
            findBy(AccountConstants.ID_IS_ONE);

            verify(accountRepositoyMock).findById(new AccountId(AccountConstants.ID_IS_ONE));
        }

        @Test
        void givenNotFoundAccountShouldThrowAccountNotFound() {
            try {
                accountService.findBy(AccountConstants.ID_IS_ONE);
                fail();
            } catch (AccountNotFoundException cause) {
                String expected = "Account not found!";

                assertEquals(expected, cause.getMessage());
            }
        }

        @Test
        void givenFoundAccountShouldReturnAccount() {
            Account account = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .agencyId(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AccountConstants.NUMBER_IS_1234)
                    .digit(AccountConstants.DIGIT_IS_2)
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .customerId(new CustomerId(CustomerConstants.ID_IS_ONE))
                    .build();

            given(accountRepositoyMock.findById(any())).willReturn(Optional.of(account));

            Account actual = accountService.findBy(AccountConstants.ID_IS_ONE);

            Account expected =  Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .agencyId(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AccountConstants.NUMBER_IS_1234)
                    .digit(AccountConstants.DIGIT_IS_2)
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .customerId(new CustomerId(CustomerConstants.ID_IS_ONE))
                    .build();

            assertEquals(expected, actual);
        }

        private Account findBy(String id) {
            try {
                return accountService.findBy(id);
            } catch (Exception cause) {
                return null;
            }
        }
    }

    @Nested
    class WhenSave {

        @Test
        void shouldSaveAccount() {
            given(identityGeneratorMock.next()).willReturn(AccountConstants.ID_IS_ONE);

            Account account = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .agencyId(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AccountConstants.NUMBER_IS_1234)
                    .digit(AccountConstants.DIGIT_IS_2)
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .customerId(new CustomerId(CustomerConstants.ID_IS_ONE))
                    .build();

            accountService.save(account);

            Account expected = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .agencyId(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AccountConstants.NUMBER_IS_1234)
                    .digit(AccountConstants.DIGIT_IS_2)
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .customerId(new CustomerId(CustomerConstants.ID_IS_ONE))
                    .build();

            verify(accountRepositoyMock).save(expected);
        }

        @Test
        void givenSavedAccountShouldReturnSavedAccount() {
            given(identityGeneratorMock.next()).willReturn(AccountConstants.ID_IS_ONE);

            Account account = Account.builder()
                    .build();

            Account actual = accountService.save(account);

            Account expected = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .build();

            assertEquals(expected, actual);
        }

    }


}