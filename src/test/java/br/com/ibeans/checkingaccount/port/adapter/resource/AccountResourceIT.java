package br.com.ibeans.checkingaccount.port.adapter.resource;

import br.com.ibeans.checkingaccount.CheckingAccountConstants.AccountConstants;
import br.com.ibeans.checkingaccount.CheckingAccountConstants.AgencyConstants;
import br.com.ibeans.checkingaccount.CheckingAccountConstants.CustomerConstants;
import br.com.ibeans.checkingaccount.CheckingAccountConstants.DocumentConstants;
import br.com.ibeans.checkingaccount.domain.model.account.Account;
import br.com.ibeans.checkingaccount.domain.model.account.AccountId;
import br.com.ibeans.checkingaccount.domain.model.agency.Agency;
import br.com.ibeans.checkingaccount.domain.model.agency.AgencyId;
import br.com.ibeans.checkingaccount.domain.model.customer.Customer;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerId;
import br.com.ibeans.checkingaccount.domain.model.customer.Document;
import br.com.ibeans.checkingaccount.domain.model.customer.DocumentType;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class AccountResourceIT extends AbstractResourceIT {

    @Nested
    class WhenSave {

        @Test
        public void shouldSuccessfully() throws Exception {
            Agency agency = Agency.builder()
                                  .id(new AgencyId(AgencyConstants.ID_IS_ONE))
                                  .number(AgencyConstants.NUMBER_IS_4321)
                                  .build();

            getEntityManager().persist(agency);

            JSONObject account = createAccount(AgencyConstants.ID_IS_ONE,
                    AccountConstants.NUMBER_IS_1234,
                    AccountConstants.DIGIT_IS_2,
                    AccountConstants.AMOUNT_IS_10,
                    CustomerConstants.ID_IS_ONE);

            getMvcMock().perform(post("/api/v1/accounts")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(account.toString()))
                        .andExpect(status().isOk());
        }

    }

    @Nested
    class WhenGet {

        @Test
        public void shouldSuccessfully() throws Exception {
            Agency agency = Agency.builder()
                    .id(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AgencyConstants.NUMBER_IS_4321)
                    .build();

            getEntityManager().persist(agency);

            Account account = Account.builder()
                    .id(new AccountId(AccountConstants.ID_IS_ONE))
                    .agencyId(new AgencyId(AgencyConstants.ID_IS_ONE))
                    .number(AccountConstants.NUMBER_IS_1234)
                    .digit(AccountConstants.DIGIT_IS_2)
                    .amount(AccountConstants.AMOUNT_IS_10)
                    .customerId(new CustomerId(CustomerConstants.ID_IS_ONE))
                    .build();

            getEntityManager().persist(account);

            getMvcMock().perform(get("/api/v1/accounts/{ID}", AccountConstants.ID_IS_ONE))
                    .andExpect(status().isOk());
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
