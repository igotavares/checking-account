package br.com.ibeans.checkingaccount.port.adapter.service.customer.impl;

import br.com.ibeans.checkingaccount.CheckingAccountConstants.*;
import br.com.ibeans.checkingaccount.domain.model.customer.Customer;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerId;
import br.com.ibeans.checkingaccount.domain.model.customer.Document;
import br.com.ibeans.checkingaccount.domain.model.customer.DocumentType;
import br.com.ibeans.checkingaccount.port.adapter.service.customer.CustomerAdapter;
import br.com.ibeans.checkingaccount.port.adapter.service.customer.Translator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.util.ReflectionTestUtils.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class HttpCustomerAdapterImplTest {

    @InjectMocks
    private HttpCustomerAdapterImpl customerAdapter;

    @Mock
    private RestTemplate restTemplateMock;

    @Mock
    private Translator<Customer> customerTranslatorMock;

    @Mock
    private ResponseEntity<String> responseEntityMock;

    private static final String URI = "http://localhost:8082/customers";
    private static final String BODY = "{\"name\": \"Pedro\"}";

    @Nested
    class WhenToCustomer {

        @BeforeEach
        void context() {
            setField(customerAdapter, "uri", "http://localhost:8082/customers");

            given(restTemplateMock.getForEntity(anyString(), eq(String.class))).willReturn(responseEntityMock);
            given((responseEntityMock.getBody())).willReturn(BODY);
        }

        @Test
        void shouldCallGetForEntity() {
            customerAdapter.toCustomer(DocumentConstants.NUMBER_IS_13573406050);

            verify(restTemplateMock).getForEntity(URI + "?documentNumber=" + DocumentConstants.NUMBER_IS_13573406050, String.class);
        }

        @Test
        void givenReturnCustomerShouldCallTo() {
            customerAdapter.toCustomer(DocumentConstants.NUMBER_IS_13573406050);

            verify(customerTranslatorMock).to(BODY);
        }

        @Test
        void shouldReturnCustomer() {
            Customer customer = Customer.builder()
                    .id(new CustomerId(CustomerConstants.ID_IS_ONE))
                    .name(CustomerConstants.NAME_IS_PEDRO_COSTA)
                    .document(Document.builder()
                                      .number(DocumentConstants.NUMBER_IS_06045672322)
                                      .type(DocumentType.CPF)
                                .build())
                    .build();

            given(customerTranslatorMock.to(BODY)).willReturn(customer);

            Customer actual = customerAdapter.toCustomer(DocumentConstants.NUMBER_IS_13573406050);

            String expected = "Customer(id=CustomerId(id=6545a607-2efe-4492-bdcf-d2d1f458d613), name=Pedro Costa, document=Document(type=CPF, number=06045672322))";

            assertEquals(expected, actual.toString());
        }

    }

}