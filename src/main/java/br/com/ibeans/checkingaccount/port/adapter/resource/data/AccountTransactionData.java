package br.com.ibeans.checkingaccount.port.adapter.resource.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountTransactionData {

    private Long id;
    private String accountId;
    private Long agency;
    private Long number;
    private Integer digit;
    private CustomerTransactionData customer;

}
