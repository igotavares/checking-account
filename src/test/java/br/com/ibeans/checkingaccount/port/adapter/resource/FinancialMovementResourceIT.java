package br.com.ibeans.checkingaccount.port.adapter.resource;

import br.com.ibeans.checkingaccount.CheckingAccountConstants;
import br.com.ibeans.checkingaccount.domain.model.account.*;
import br.com.ibeans.checkingaccount.domain.model.agency.Agency;
import br.com.ibeans.checkingaccount.domain.model.agency.AgencyId;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerId;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class FinancialMovementResourceIT extends AbstractResourceIT {

    @Nested
    class WhenGetAll {

        @Test
        public void shouldFindFinancialMovements() throws Exception {
            Agency agency = Agency.builder()
                    .id(new AgencyId(CheckingAccountConstants.AgencyConstants.ID_IS_ONE))
                    .number(CheckingAccountConstants.AgencyConstants.NUMBER_IS_4321)
                    .build();

            getEntityManager().persist(agency);

            Account account = Account.builder()
                    .id(new AccountId(CheckingAccountConstants.AccountConstants.ID_IS_ONE))
                    .agencyId(new AgencyId(CheckingAccountConstants.AgencyConstants.ID_IS_ONE))
                    .number(CheckingAccountConstants.AccountConstants.NUMBER_IS_1234)
                    .digit(CheckingAccountConstants.AccountConstants.DIGIT_IS_2)
                    .amount(CheckingAccountConstants.AccountConstants.AMOUNT_IS_10)
                    .customerId(new CustomerId(CheckingAccountConstants.CustomerConstants.ID_IS_ONE))
                    .build();

            getEntityManager().persist(account);

            FinancialMovement financialMovement = FinancialMovement.builder()
                    .id(new FinancialMovementId(CheckingAccountConstants.FinancialMovementConstants.ID_IS_ONE))
                    .from(new AccountId(CheckingAccountConstants.AccountConstants.ID_IS_ONE))
                    .to(new AccountId(CheckingAccountConstants.AccountConstants.ID_IS_ONE))
                    .type(FinancialMovementType.DEBIT)
                    .amount(CheckingAccountConstants.AccountConstants.AMOUNT_IS_10)
                    .build();

            getEntityManager().persist(financialMovement);

            getMvcMock().perform(get("/api/v1/accounts/{id}/financialMovements", CheckingAccountConstants.AccountConstants.ID_IS_ONE))
                    .andExpect(status().isOk());
        }

    }

}