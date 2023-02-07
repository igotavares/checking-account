package br.com.ibeans.checkingaccount.domain.model.transaction;

import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.shared.ValueObject;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "account_transaction")
public class AccountTransaction extends ValueObject implements Serializable {

    @Embedded
    @AttributeOverride(name = "id", column =
        @Column(name = "ACCOUNT_ID"))
    private AccountId accountId;
    @Column(name = "AGENCY_ID")
    private Long agency;
    private Long number;
    private Integer digit;
    @Embedded
    private CustomerTransaction customer;

    public String accountId() {
        return id(accountId);
    }

}
