package br.com.ibeans.checkingaccount.port.adapter.service.customer.impl;

import br.com.ibeans.checkingaccount.CheckingAccountConstants.*;
import br.com.ibeans.checkingaccount.domain.model.customer.Customer;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerId;
import br.com.ibeans.checkingaccount.domain.model.customer.Document;
import br.com.ibeans.checkingaccount.domain.model.customer.DocumentType;
import br.com.ibeans.checkingaccount.port.adapter.service.customer.CustomerAdapter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TranslatingCustomerServiceImplTest {

    @InjectMocks
    private TranslatingCustomerServiceImpl customerService;

    @Mock
    private CustomerAdapter customerAdapterMock;

    @Nested
    class WhenCustomerFrom {

        @Test
        void shouldCallToCustomer() {
            customerService.customerFrom(DocumentConstants.NUMBER_IS_13573406050);

            verify(customerAdapterMock).toCustomer(DocumentConstants.NUMBER_IS_13573406050);
        }

        @Test
        void shouldReturnCustomer() {
            Customer customer = Customer.builder()
                                        .id(new CustomerId(CustomerConstants.ID_IS_ONE))
                                        .name(CustomerConstants.NAME_IS_PEDRO_COSTA)
                                        .document(Document.builder()
                                                          .type(DocumentType.CPF)
                                                          .number(DocumentConstants.NUMBER_IS_13573406050)
                                                          .build())
                                        .build();

            given(customerAdapterMock.toCustomer(DocumentConstants.NUMBER_IS_13573406050)).willReturn(customer);

            Customer actual = customerService.customerFrom(DocumentConstants.NUMBER_IS_13573406050);

            String expected = "Customer(id=CustomerId(id=6545a607-2efe-4492-bdcf-d2d1f458d613), name=Pedro Costa, document=Document(type=CPF, number=13573406050))";

            assertEquals(expected, actual.toString());
        }
    }

}