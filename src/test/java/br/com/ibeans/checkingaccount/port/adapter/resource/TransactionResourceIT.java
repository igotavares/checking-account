package br.com.ibeans.checkingaccount.port.adapter.resource;

import br.com.ibeans.checkingaccount.CheckingAccountConstants.*;
import br.com.ibeans.checkingaccount.domain.model.account.Account;
import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.agency.Agency;
import br.com.ibeans.checkingaccount.domain.model.agency.AgencyId;
import br.com.ibeans.checkingaccount.domain.model.customer.*;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.AccountTransactionData;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.CustomerTransactionData;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.TransactionData;
import br.com.ibeans.checkingaccount.port.adapter.service.customer.CustomerAdapter;
import br.com.ibeans.checkingaccount.port.adapter.service.transaction.NotificationTransactionAdapter;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TransactionResourceIT extends AbstractResourceIT {

    @Autowired
    JacksonTester<TransactionData> transactionConverter;

    @MockBean
    CustomerAdapter customerAdapterMock;

    @MockBean
    NotificationTransactionAdapter notificationTransactionAdapterMock;

    @Nested
    class WhenCreate {

        @Test
        public void shouldSuccessfully() throws Exception {
            Agency agency = Agency.builder()
                    .id(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AgencyConstants.NUMBER_IS_4321)
                    .build();

            getEntityManager().persist(agency);

            Account account = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .customerId(new CustomerId(CustomerConstants.ID_IS_ONE))
                    .agencyId(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AccountConstants.NUMBER_IS_1234)
                    .digit(AccountConstants.DIGIT_IS_1)
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .build();

            getEntityManager().persist(account);

            agency = Agency.builder()
                    .id(new AgencyId(AgencyConstants.ID_IS_TWO))
                    .number(AgencyConstants.NUMBER_IS_5678)
                    .build();

            getEntityManager().persist(agency);

            account = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_TWO))
                    .customerId(new CustomerId(CustomerConstants.ID_IS_TWO))
                    .agencyId(new AgencyId(AgencyConstants.ID_IS_TWO))
                    .number(AccountConstants.NUMBER_IS_4321)
                    .digit(AccountConstants.DIGIT_IS_2)
                    .amount(AccountConstants.AMOUNT_IS_20)
                    .build();

            getEntityManager().persist(account);

            TransactionData transaction = TransactionData.builder()
                    .accountFrom(AccountTransactionData.builder()
                            .accountId(AccountConstants.ID_IS_ONE)
                            .build())
                    .accountTo(AccountTransactionData.builder()
                            .agency(AgencyConstants.NUMBER_IS_5678)
                            .number(AccountConstants.NUMBER_IS_4321)
                            .digit(AccountConstants.DIGIT_IS_2)
                            .customer(CustomerTransactionData.builder()
                                                            .name(CustomerConstants.NAME_IS_PEDRO_COSTA)
                                                            .documentNumber(DocumentConstants.NUMBER_IS_06045672322)
                                                            .build())
                            .build())
                    .amount(TransactionConstants.AMOUNT_IS_10)
                    .build();

            Customer customer = Customer.builder()
                    .id(new CustomerId(CustomerConstants.ID_IS_TWO))
                    .document(Document.builder()
                            .number(DocumentConstants.NUMBER_IS_06045672322)
                            .type(DocumentType.CPF)
                            .build())
                    .build();

            given(customerAdapterMock.toCustomer(DocumentConstants.NUMBER_IS_06045672322)).willReturn(customer);

            String body = transactionConverter.write(transaction).getJson();

            getMvcMock().perform(post("/api/v1/transactions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").isNotEmpty());
        }

    }

}