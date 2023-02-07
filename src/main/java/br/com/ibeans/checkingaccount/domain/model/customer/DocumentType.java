package br.com.ibeans.checkingaccount.domain.model.customer;

import java.util.stream.Stream;

public enum DocumentType {
    CPF,
    CNPJ;

    public static DocumentType from(String name) {
        return Stream.of(values())
                .filter(type -> type.name().equals(name))
                .findFirst()
                .orElse(null);
    }

}
