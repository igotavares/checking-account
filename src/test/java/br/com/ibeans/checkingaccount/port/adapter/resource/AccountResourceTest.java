package br.com.ibeans.checkingaccount.port.adapter.resource;

import br.com.ibeans.checkingaccount.CheckingAccountConstants.*;
import br.com.ibeans.checkingaccount.application.AccountService;
import br.com.ibeans.checkingaccount.domain.model.account.Account;
import br.com.ibeans.checkingaccount.port.adapter.resource.converter.AccountConverter;
import org.json.JSONObject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

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

        @Test
        public void shouldSuccessfullyChange() throws Exception {
            JSONObject account = new JSONObject();
            account.put("nember", AccountConstants.NUMBER_IS_1234);

            mvcMock.perform(post("/api/v1/accounts")
                    .accept(MediaType.APPLICATION_JSON)
                    .content(account.toString()));

            Account expected = Account.builder()
                                      .number(AccountConstants.NUMBER_IS_1234)
                                       .build();

            verify(accountServiceMock).save(expected);
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

}