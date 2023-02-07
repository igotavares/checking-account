package br.com.ibeans.checkingaccount.application;

import br.com.ibeans.checkingaccount.domain.model.account.Account;

public interface AccountService {

    Account findBy(String id);

    Account save(Account account);

}
