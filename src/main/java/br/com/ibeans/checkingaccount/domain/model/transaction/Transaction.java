package br.com.ibeans.checkingaccount.domain.model.transaction;


import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.shared.AggregateRoot;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Optional;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "transaction")
public class Transaction extends AggregateRoot<TransactionId> {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_FROM", referencedColumnName = "id")
    private AccountTransaction accountFrom;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ACCOUNT_TO", referencedColumnName = "id")
    private AccountTransaction accountTo;
    private BigDecimal amount;

    public Transaction(TransactionId id) {
        super(id);
    }

    public AccountId accountIdFrom() {
        return accountFrom.getAccountId();
    }

    public Optional<AccountTransaction> accountTo() {
        return Optional.ofNullable(accountTo);
    }
}
