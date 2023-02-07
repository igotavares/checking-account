package br.com.ibeans.checkingaccount.domain.model.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    private DocumentType type;
    private String number;

}
