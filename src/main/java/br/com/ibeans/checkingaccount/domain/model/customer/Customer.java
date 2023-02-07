package br.com.ibeans.checkingaccount.domain.model.customer;

import br.com.ibeans.checkingaccount.domain.model.shared.AggregateRoot;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@SuperBuilder
public class Customer {

    private CustomerId id;
    private String name;
    private Document document;

}
