package br.com.ibeans.checkingaccount.port.adapter.persistence;


import br.com.ibeans.checkingaccount.domain.model.account.Account;
import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface AccountRepositoy extends CrudRepository<Account, AccountId> {

    @Override
    Optional<Account> findById(AccountId accountId);

    @Query("from Account as ac where ac.agencyId.id in (select ag.id.id from Agency ag where ag.number = :agencyNumber) and ac.number = :number and ac.digit = :digit ")
    Optional<Account> findBy(Long agencyNumber, Long number, Integer digit);

}
