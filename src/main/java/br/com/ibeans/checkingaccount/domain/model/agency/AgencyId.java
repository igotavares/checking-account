package br.com.ibeans.checkingaccount.domain.model.agency;

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
public class AgencyId implements Identity, Serializable {

    private String id;

}
