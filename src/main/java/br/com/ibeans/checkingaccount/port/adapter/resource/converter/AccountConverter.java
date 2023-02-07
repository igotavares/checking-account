package br.com.ibeans.checkingaccount.port.adapter.resource.converter;

import br.com.ibeans.checkingaccount.domain.model.account.Account;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.AccountData;

public interface AccountConverter extends Converter<Account, AccountData> {
}
