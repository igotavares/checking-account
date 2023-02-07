package br.com.ibeans.checkingaccount.domain.model.transaction;

import br.com.ibeans.checkingaccount.CheckingAccountConstants.*;
import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerId;
import br.com.ibeans.checkingaccount.port.adapter.persistence.FinancialMovementRepository;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class DailyLimitSpecTest {

    @InjectMocks
    private DailyLimitSpec dailyLimitSpec;

    @Mock
    private FinancialMovementRepository financialMovementRepositoryMock;

    @Nested
    class WhenExceeds {

        @Test
        void given9999AmountShouldNotExceedLimit() {
            given(financialMovementRepositoryMock.findTotalAmountDebitedBy(any()))
                    .willReturn(BigDecimal.ZERO);

            dailyLimitSpec.exceeded(new AccountId(AccountConstants.ID_IS_ONE), BigDecimal.valueOf(9999));
        }

        @Test
        void give10000AmountShoulddNotExceedLimit() {
            given(financialMovementRepositoryMock.findTotalAmountDebitedBy(any()))
                    .willReturn(BigDecimal.ZERO);

            dailyLimitSpec.exceeded(new AccountId(AccountConstants.ID_IS_ONE), BigDecimal.valueOf(10000));
        }

        @Test
        void give10001AmountShouldExceedLimit() {
            try {
                dailyLimitSpec.exceeded(new AccountId(AccountConstants.ID_IS_ONE), BigDecimal.valueOf(10001));
                fail();
            } catch (DailyLimitExceededException cause) {
                String expected = "Daily limit exceeded";
                assertEquals(expected, cause.getMessage());
            }
        }

        @Test
        void given5000And5001AmountShouldExceedLimit() {
            given(financialMovementRepositoryMock.findTotalAmountDebitedBy(any()))
                    .willReturn(BigDecimal.valueOf(5001));

            try {
                dailyLimitSpec.exceeded(new AccountId(AccountConstants.ID_IS_ONE), BigDecimal.valueOf(5001));
                fail();
            } catch (DailyLimitExceededException cause) {
                String expected = "Daily limit exceeded";
                assertEquals(expected, cause.getMessage());
            }
        }

    }

}