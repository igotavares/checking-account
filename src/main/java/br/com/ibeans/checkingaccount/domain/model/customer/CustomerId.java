package br.com.ibeans.checkingaccount.domain.model.customer;

import br.com.ibeans.checkingaccount.domain.model.shared.Identity;
import lombok.*;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerId implements Identity, Serializable {

    private String id;

}
