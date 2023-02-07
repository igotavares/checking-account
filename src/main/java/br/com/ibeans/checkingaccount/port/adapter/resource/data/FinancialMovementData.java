package br.com.ibeans.checkingaccount.port.adapter.resource.data;

import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.account.FinancialMovementType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Enumerated;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinancialMovementData {

    private String id;
    private String accountIdFrom;
    private String accountIdTo;
    private String type;
    private BigDecimal amount;

}
