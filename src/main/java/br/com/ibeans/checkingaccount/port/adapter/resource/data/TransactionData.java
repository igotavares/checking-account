package br.com.ibeans.checkingaccount.port.adapter.resource.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionData {

    private String id;
    @NotNull
    private AccountTransactionData accountFrom;
    @NotNull
    private AccountTransactionData accountTo;
    @NotNull
    private BigDecimal amount;

    public TransactionData(String id) {
        this.id = id;
    }
}
