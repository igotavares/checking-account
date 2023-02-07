package br.com.ibeans.checkingaccount.domain.model.account;

import br.com.ibeans.checkingaccount.domain.model.agency.AgencyId;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerId;
import br.com.ibeans.checkingaccount.domain.model.shared.AggregateRoot;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "account")
public class Account extends AggregateRoot<AccountId> {

    private Long number;
    private Integer digit;
    private BigDecimal amount;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "CUSTEMER_ID"))
    private CustomerId customerId;
    @Embedded
    @AttributeOverride(name = "id", column = @Column(name = "AGENCY_ID"))
    private AgencyId agencyId;

    public Account(AccountId id) {
        super(id);
    }

    public void setId(String id) {
        setId(new AccountId(id));
    }

    public String custemerId() {
        return id(customerId);
    }

    public void debit(BigDecimal amount) {
        suffientBalance(amount);
        this.amount = this.amount.subtract(amount);
    }

    private void suffientBalance(BigDecimal amount) {
        if (this.amount.compareTo(amount) < 0) {
            throw new InsufficientBalanceException();
        }
    }

    public void credit(BigDecimal amount) {
        this.amount = this.amount.add(amount);
    }

    public String agencyId() {
        return id(agencyId);
    }

    public void validCustomer(CustomerId customerId) {
        if (this.customerId != null && !this.customerId.equals(customerId)) {
            throw new AccountIsNotTheCustomerException();
        }
    }

    public FinancialMovement createDebitMovement(String id, BigDecimal amount, AccountId accountTo) {
        return FinancialMovement.builder()
                .id(new FinancialMovementId(id))
                .type(FinancialMovementType.DEBIT)
                .from(getId())
                .to(accountTo)
                .amount(amount)
                .build();
    }

    public FinancialMovement createCreditMovement(String id, BigDecimal amount, AccountId accountFrom) {
        return FinancialMovement.builder()
                .id(new FinancialMovementId(id))
                .type(FinancialMovementType.CREDIT)
                .from(accountFrom)
                .to(getId())
                .amount(amount)
                .build();
    }
}
