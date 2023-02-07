package br.com.ibeans.checkingaccount.port.adapter.resource.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTransactionData {

    private String name;
    private String documentNumber;

}
