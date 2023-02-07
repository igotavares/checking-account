package br.com.ibeans.checkingaccount.domain.model.account;

import br.com.ibeans.checkingaccount.domain.model.shared.Identity;
import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FinancialMovementId implements Identity, Serializable {

    private String id;

}
