package br.com.ibeans.checkingaccount.port.adapter.persistence;

import br.com.ibeans.checkingaccount.CheckingAccountConstants.AccountConstants;
import br.com.ibeans.checkingaccount.CheckingAccountConstants.AgencyConstants;
import br.com.ibeans.checkingaccount.CheckingAccountConstants.CustomerConstants;
import br.com.ibeans.checkingaccount.domain.model.account.Account;
import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.agency.Agency;
import br.com.ibeans.checkingaccount.domain.model.agency.AgencyId;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class AccountRepositoyIT extends AbstractJPAIT {

    @Autowired
    private AccountRepositoy accountRepositoy;

    @Nested
    class WhenFindByAgencyNumberAndNumberAndDigit {
        @Test
        void shouldNotFound() {
            Agency agency = Agency.builder()
                    .id(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AgencyConstants.NUMBER_IS_4321)
                    .build();

            getEntityManager().persist(agency);

            Account account = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .agencyId(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AccountConstants.NUMBER_IS_1234)
                    .digit(AccountConstants.DIGIT_IS_2)
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .customerId(new CustomerId(CustomerConstants.ID_IS_ONE))
                    .build();

            getEntityManager().persist(account);

            Account actual = accountRepositoy
                    .findBy(AgencyConstants.NUMBER_IS_5678,
                            AccountConstants.NUMBER_IS_1234,
                            AccountConstants.DIGIT_IS_2)
                    .orElse(null);

            assertNull(actual);
        }

        @Test
        void shouldFound() {
            Agency agency = Agency.builder()
                    .id(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AgencyConstants.NUMBER_IS_4321)
                    .build();

            getEntityManager().persist(agency);

            Account account = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .agencyId(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AccountConstants.NUMBER_IS_1234)
                    .digit(AccountConstants.DIGIT_IS_2)
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .customerId(new CustomerId(CustomerConstants.ID_IS_ONE))
                    .build();

            getEntityManager().persist(account);

            Account actual = accountRepositoy
                    .findBy(AgencyConstants.NUMBER_IS_4321,
                            AccountConstants.NUMBER_IS_1234,
                            AccountConstants.DIGIT_IS_2)
                    .orElse(null);

            assertNotNull(actual);
        }

    }

}