package br.com.ibeans.checkingaccount.port.adapter.resource.converter.impl;

import br.com.ibeans.checkingaccount.domain.model.account.Account;
import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.agency.AgencyId;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerId;
import br.com.ibeans.checkingaccount.port.adapter.resource.converter.AccountConverter;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.AccountData;
import org.springframework.stereotype.Component;

@Component
public class AccountConverterImpl implements AccountConverter {

    public AccountData toData(Account account) {
        return AccountData.builder()
                .id(account.id())
                .agencyId(account.agencyId())
                .number(account.getNumber())
                .digit(account.getDigit())
                .amount(account.getAmount())
                .custemerId(account.custemerId())
                .version(account.getVersion())
                .createdOn(account.getCreatedOn())
                .updatedOn(account.getUpdatedOn())
                .build();
    }

    public Account toEntity(AccountData account) {
        return Account.builder()
                .id(createAccountId(account.getId()))
                .agencyId(createAgencyId(account.getAgencyId()))
                .number(account.getNumber())
                .digit(account.getDigit())
                .amount(account.getAmount())
                .customerId(createCustemerId(account.getCustemerId()))
                .version(account.getVersion())
                .createdOn(account.getCreatedOn())
                .updatedOn(account.getUpdatedOn())
                .build();
    }

    private AgencyId createAgencyId(String id) {
        if (id != null) {
            return new AgencyId(id);
        }
        return null;
    }

    private CustomerId createCustemerId(String id) {
        if (id != null) {
            return new CustomerId(id);
        }
        return null;
    }

    private AccountId createAccountId(String id) {
        if (id != null) {
            return new AccountId(id);
        }
        return null;
    }

}
