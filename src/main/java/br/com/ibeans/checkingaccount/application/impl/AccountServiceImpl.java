package br.com.ibeans.checkingaccount.application.impl;

import br.com.ibeans.checkingaccount.application.AccountService;
import br.com.ibeans.checkingaccount.domain.model.account.Account;
import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.account.AccountNotFoundException;
import br.com.ibeans.checkingaccount.domain.model.shared.IdentityGenerator;
import br.com.ibeans.checkingaccount.port.adapter.persistence.AccountRepositoy;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private IdentityGenerator identityGenerator;
    private AccountRepositoy accountRepositoy;

    @Override
    public Account findBy(String id) {
        return accountRepositoy.findById(createAccountId(id))
                .orElseThrow(AccountNotFoundException::new);
    }

    private AccountId createAccountId(String id) {
        return new AccountId(id);
    }

    @Override
    public Account save(Account account) {
        account.setId(identityGenerator.next());
        accountRepositoy.save(account);
        return new Account(account.getId());
    }

}
