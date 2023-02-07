package br.com.ibeans.checkingaccount.domain.model.account;

import br.com.ibeans.checkingaccount.domain.model.shared.AggregateRoot;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Entity
@SuperBuilder
@Table(name = "financial_movement")
public class FinancialMovement extends AggregateRoot<FinancialMovementId> {

    @Embedded
    @AttributeOverride(name = "id", column =
        @Column(name = "ACCOUNT_FROM"))
    private AccountId from;
    @Embedded
    @AttributeOverride(name = "id", column =
        @Column(name = "ACCOUNT_TO"))
    private AccountId to;
    @Enumerated
    private FinancialMovementType type;
    private BigDecimal amount;

    public String accountIdFrom() {
        return id(from);
    }

    public String accountIdTo() {
        return id(to);
    }

}
