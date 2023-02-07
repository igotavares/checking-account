package br.com.ibeans.checkingaccount.port.adapter.resource.converter.impl;

import br.com.ibeans.checkingaccount.domain.model.account.FinancialMovement;
import br.com.ibeans.checkingaccount.port.adapter.resource.converter.FinancialMovementConverter;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.FinancialMovementData;
import org.springframework.stereotype.Component;

@Component
public class FinacialMovementConverterImpl implements FinancialMovementConverter {

    @Override
    public FinancialMovementData toData(FinancialMovement financialMovement) {
        return FinancialMovementData.builder()
                .id(financialMovement.id())
                .accountIdFrom(financialMovement.accountIdFrom())
                .accountIdTo(financialMovement.accountIdTo())
                .type(getType(financialMovement))
                .amount(financialMovement.getAmount())
                .build();
    }

    private String getType(FinancialMovement financialMovement) {
        if (financialMovement.getType() != null) {
            return financialMovement.getType().name();
        }
        return null;
    }

    @Override
    public FinancialMovement toEntity(FinancialMovementData financialMovementData) {
        return null;
    }
}
