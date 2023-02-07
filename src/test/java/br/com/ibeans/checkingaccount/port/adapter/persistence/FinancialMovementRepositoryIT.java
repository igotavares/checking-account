package br.com.ibeans.checkingaccount.port.adapter.persistence;

import br.com.ibeans.checkingaccount.CheckingAccountConstants.*;
import br.com.ibeans.checkingaccount.domain.model.account.*;
import br.com.ibeans.checkingaccount.domain.model.agency.Agency;
import br.com.ibeans.checkingaccount.domain.model.agency.AgencyId;
import br.com.ibeans.checkingaccount.domain.model.customer.Customer;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerId;
import br.com.ibeans.checkingaccount.domain.model.customer.Document;
import br.com.ibeans.checkingaccount.domain.model.customer.DocumentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FinancialMovementRepositoryIT extends AbstractJPAIT {

    @Autowired
    private FinancialMovementRepository financialMovementRepository;

    @Nested
    class WhenFind {
        
        @Test
        void givenNoFinancialMovementShouldBeZero() {
            BigDecimal actual = financialMovementRepository.findTotalAmountDebitedBy(AccountConstants.ID_IS_ONE);

            BigDecimal expected = BigDecimal.ZERO.setScale(2);

            assertEquals(expected, actual);
        }

        @Test
        void givenOneCreditFinancialMovementShouldBe10() {
            Agency agency = Agency.builder()
                    .id(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AgencyConstants.NUMBER_IS_4321)
                    .build();

            getEntityManager().persist(agency);

            getEntityManager().persist(agency);

            Account account = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .agencyId(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AccountConstants.NUMBER_IS_1234)
                    .digit(AccountConstants.DIGIT_IS_2)
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .customerId(new CustomerId(CustomerConstants.ID_IS_ONE))
                    .build();

            FinancialMovement financialMovement = FinancialMovement.builder()
                    .id(new FinancialMovementId(FinancialMovementConstants.ID_IS_ONE))
                    .from(new AccountId(AccountConstants.ID_IS_ONE))
                    .to(new AccountId(AccountConstants.ID_IS_ONE))
                    .type(FinancialMovementType.DEBIT)
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .build();

            getEntityManager().persist(financialMovement);

            BigDecimal actual = financialMovementRepository.findTotalAmountDebitedBy(AccountConstants.ID_IS_ONE);

            BigDecimal expected = BigDecimal.ZERO.setScale(2);

            assertEquals(expected, actual);
        }

    }
    
    
}