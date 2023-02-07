package br.com.ibeans.checkingaccount.port.adapter.service.customer.impl;

import br.com.ibeans.checkingaccount.domain.model.customer.Customer;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerId;
import br.com.ibeans.checkingaccount.domain.model.customer.Document;
import br.com.ibeans.checkingaccount.domain.model.customer.DocumentType;
import br.com.ibeans.checkingaccount.port.adapter.service.customer.Translator;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.sql.ConnectionBuilder;

@Component
public class CustomerTranslatorImpl implements Translator<Customer> {

    public Customer to(String customer) {
        JSONObject customerJson = new JSONObject(customer);
        return Customer.builder()
                .id(createCustomerId(customerJson.optString("id")))
                .name(customerJson.optString("name"))
                .document(createDocument(customerJson.optString("documentNumber"),
                                         customerJson.optString("documentType")))
                .build();
    }

    private Document createDocument(String number, String type) {
        if (number != null || type != null) {
            return Document.builder()
                    .number(number)
                    .type(DocumentType.from(type))
                    .build();
        }
        return null;
    }

    private CustomerId createCustomerId(String id) {
        if (id != null) {
            return new CustomerId(id);
        }
        return null;
    }

}
