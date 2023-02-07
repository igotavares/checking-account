package br.com.ibeans.checkingaccount.port.adapter.resource.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountData {

    private String id;
    @NotEmpty
    private String agencyId;
    @NotNull
    private Long number;
    @NotNull
    private Integer digit;
    @NotNull
    private BigDecimal amount;
    private String custemerId;
    private Long version;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
