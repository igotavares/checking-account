package br.com.ibeans.checkingaccount.domain.model.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class CustomerTransaction {

    @Column(name = "CUSTEMER_ID")
    private String id;
    @Column(name = "CUSTOMER_NAME")
    private String name;
    @Column(name = "DOCUMENT_NUMBER")
    private String documentNumber;

}
