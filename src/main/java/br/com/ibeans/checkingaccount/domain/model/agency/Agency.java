package br.com.ibeans.checkingaccount.domain.model.agency;

import br.com.ibeans.checkingaccount.domain.model.shared.AggregateRoot;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "agency")
public class Agency extends AggregateRoot<AgencyId> {

   private Long number;

}
