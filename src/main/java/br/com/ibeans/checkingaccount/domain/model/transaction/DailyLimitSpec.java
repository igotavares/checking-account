
package br.com.ibeans.checkingaccount.domain.model.transaction;

import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.port.adapter.persistence.FinancialMovementRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@AllArgsConstructor
public class DailyLimitSpec {

    private static final BigDecimal DAILY_LIMIT = BigDecimal.valueOf(10000);

    private FinancialMovementRepository financialMovementRepository;

    public void exceeded(AccountId accountId, BigDecimal amount) {
        if (DAILY_LIMIT.compareTo(amount) < 0) {
            throw new DailyLimitExceededException();
        }
        BigDecimal totalAmountDebited = financialMovementRepository.findTotalAmountDebitedBy(accountId.getId());
        if (DAILY_LIMIT.subtract(totalAmountDebited).compareTo(amount) < 0) {
            throw new DailyLimitExceededException();
        }
    }

}
