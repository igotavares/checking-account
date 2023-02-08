package br.com.ibeans.checkingaccount.port.adapter.resource;

import br.com.ibeans.checkingaccount.CheckingAccountConstants.AccountConstants;
import br.com.ibeans.checkingaccount.CheckingAccountConstants.AgencyConstants;
import br.com.ibeans.checkingaccount.CheckingAccountConstants.CustomerConstants;
import br.com.ibeans.checkingaccount.application.AccountService;
import br.com.ibeans.checkingaccount.domain.model.account.Account;
import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.agency.AgencyId;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerId;
import br.com.ibeans.checkingaccount.port.adapter.resource.converter.AccountConverter;
import br.com.ibeans.checkingaccount.port.adapter.resource.data.AccountData;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = AccountResource.class)
class AccountResourceTest {

    @Autowired
    private MockMvc mvcMock;

    @MockBean
    private AccountService accountServiceMock;

    @MockBean
    private AccountConverter accountConverterMock;

    @Nested
    class WhenSave {

        @BeforeEach
        void context() {
            Account convertedEntity = Account.builder().build();

            lenient().when(accountConverterMock.toEntity(any())).thenReturn(convertedEntity);

            Account savedEntity = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .build();

            lenient().when(accountServiceMock.save(any())).thenReturn(savedEntity);

        }

        @Test
        public void shouldConvertToEntity() throws Exception {
            JSONObject account = createAccount(AgencyConstants.ID_IS_ONE,
                    AccountConstants.NUMBER_IS_1234,
                    AccountConstants.DIGIT_IS_2,
                    AccountConstants.AMOUNT_IS_10,
                    CustomerConstants.ID_IS_ONE);

            mvcMock.perform(post("/api/v1/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(account.toString()));

            AccountData expected = AccountData.builder()
                                      .number(AccountConstants.NUMBER_IS_1234)
                    .agencyId(AgencyConstants.ID_IS_ONE)
                    .number(AccountConstants.NUMBER_IS_1234)
                    .digit(AccountConstants.DIGIT_IS_2)
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .custemerId(CustomerConstants.ID_IS_ONE)
                    .build();

            verify(accountConverterMock).toEntity(expected);
        }

        @Test
        public void givenConvetedEntityShouldSaveAEntity() throws Exception {
            Account convertedEntity = Account.builder()
                    .number(AccountConstants.NUMBER_IS_1234)
                    .agencyId(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AccountConstants.NUMBER_IS_1234)
                    .digit(AccountConstants.DIGIT_IS_2)
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .customerId(new CustomerId(CustomerConstants.ID_IS_ONE))
                    .build();

            given(accountConverterMock.toEntity(any())).willReturn(convertedEntity);

            JSONObject account = createAccount(AgencyConstants.ID_IS_ONE,
                    AccountConstants.NUMBER_IS_1234,
                    AccountConstants.DIGIT_IS_2,
                    AccountConstants.AMOUNT_IS_10,
                    CustomerConstants.ID_IS_ONE);

            mvcMock.perform(post("/api/v1/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(account.toString()));

            Account expected = Account.builder()
                    .number(AccountConstants.NUMBER_IS_1234)
                    .agencyId(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AccountConstants.NUMBER_IS_1234)
                    .digit(AccountConstants.DIGIT_IS_2)
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .customerId(new CustomerId(CustomerConstants.ID_IS_ONE))
                    .build();

            verify(accountServiceMock).save(expected);
        }

        @Test
        public void givenSavedEntityShouldConvetToData() throws Exception {
            Account savedEntity = Account.builder()
                                        .id(new AccountId(AccountConstants.ID_IS_ONE))
                                        .build();

            given(accountServiceMock.save(any())).willReturn(savedEntity);

            JSONObject account = createAccount(AgencyConstants.ID_IS_ONE,
                    AccountConstants.NUMBER_IS_1234,
                    AccountConstants.DIGIT_IS_2,
                    AccountConstants.AMOUNT_IS_10,
                    CustomerConstants.ID_IS_ONE);

            mvcMock.perform(post("/api/v1/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(account.toString()));

            Account expected = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .build();

            verify(accountConverterMock).toData(expected);
        }

        @Test
        public void givenConvetedDataShouldRetrunAccount() throws Exception {
            AccountData convetedData = AccountData.builder()
                    .id(AccountConstants.ID_IS_ONE)
                    .build();

            given(accountConverterMock.toData(any())).willReturn(convetedData);

            JSONObject account = createAccount(AgencyConstants.ID_IS_ONE,
                    AccountConstants.NUMBER_IS_1234,
                    AccountConstants.DIGIT_IS_2,
                    AccountConstants.AMOUNT_IS_10,
                    CustomerConstants.ID_IS_ONE);

            mvcMock.perform(post("/api/v1/accounts")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(account.toString()))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.id").isNotEmpty());
        }

    }

    @Nested
    class WhenGet {

        @Test
        public void shouldSuccessfullyChange() throws Exception {
            mvcMock.perform(get("/api/v1/accounts/{id}",
                    AccountConstants.ID_IS_ONE));

            verify(accountServiceMock).findBy(AccountConstants.ID_IS_ONE);
        }

    }

    private JSONObject createAccount(String agencyId,
                                     Long number,
                                     Integer digit,
                                     BigDecimal amount,
                                     String custemerId) throws JSONException {
        JSONObject account = new JSONObject();
        account.put("agencyId", agencyId);
        account.put("number", number);
        account.put("digit", digit);
        account.put("amount", amount);
        account.put("custemerId", custemerId);
        return account;
    }

}