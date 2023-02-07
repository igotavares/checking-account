package br.com.ibeans.checkingaccount.domain.model.transaction;

import br.com.ibeans.checkingaccount.domain.model.shared.Identity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionId implements Identity, Serializable {

    private String id;

}
