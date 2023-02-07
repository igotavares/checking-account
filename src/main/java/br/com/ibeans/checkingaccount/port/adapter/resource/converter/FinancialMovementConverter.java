package br.com.ibeans.checkingaccount.port.adapter.resource.converter;

import br.com.ibeans.checkingaccount.domain.model.account.FinancialMovement;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.FinancialMovementData;

public interface FinancialMovementConverter extends Converter<FinancialMovement, FinancialMovementData> {
}
