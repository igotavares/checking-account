package br.com.ibeans.checkingaccount.port.adapter.service.customer.impl;

import br.com.ibeans.checkingaccount.CheckingAccountConstants.*;
import br.com.ibeans.checkingaccount.domain.model.customer.Customer;
import br.com.ibeans.checkingaccount.domain.model.customer.DocumentType;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTranslatorImplTest {

    CustomerTranslatorImpl customerTranslator;

    public CustomerTranslatorImplTest() {
        this.customerTranslator = new CustomerTranslatorImpl();
    }

    @Nested
    class WhenTo {
        @Test
        void shouldReturnCustomer() {
            String body = new StringBuilder("{")
                    .append("\"id\": ").append("\"" + CustomerConstants.ID_IS_ONE + "\",")
                    .append("\"name\": ").append("\"" + CustomerConstants.NAME_IS_PEDRO_COSTA + "\",")
                    .append("\"documentNumber\": ").append("\"" + DocumentConstants.NUMBER_IS_13573406050 + "\",")
                    .append("\"documentType\": ").append("\"" + DocumentType.CPF.name() + "\"")
                    .append("}")
                    .toString();

            Customer customer = customerTranslator.to(body);

            String expected = "Customer(id=CustomerId(id=6545a607-2efe-4492-bdcf-d2d1f458d613), name=Pedro Costa, document=Document(type=CPF, number=13573406050))";

            assertEquals(expected, customer.toString());
        }
    }

}