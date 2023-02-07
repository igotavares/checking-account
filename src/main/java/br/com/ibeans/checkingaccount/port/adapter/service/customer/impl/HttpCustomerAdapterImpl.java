package br.com.ibeans.checkingaccount.port.adapter.service.customer.impl;

import br.com.ibeans.checkingaccount.domain.model.customer.Customer;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerException;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerNotFoundException;
import br.com.ibeans.checkingaccount.port.adapter.service.customer.CustomerAdapter;
import br.com.ibeans.checkingaccount.port.adapter.service.customer.Translator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Service
public class HttpCustomerAdapterImpl implements CustomerAdapter {

    private static Logger log = LoggerFactory.getLogger(HttpCustomerAdapterImpl.class);

    @Value("${service.customer.uri}")
    private String uri;

    private RestTemplate restTemplate;
    private Translator<Customer> customerTranslator;

    public HttpCustomerAdapterImpl(RestTemplate restTemplate, Translator<Customer> customerTranslator) {
        this.restTemplate = restTemplate;
        this.customerTranslator = customerTranslator;
    }

    @Override
    public Customer toCustomer(String documentNumber) {
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(uri + "?documentNumber=" + documentNumber, String.class);
            return customerTranslator.to(response.getBody());
        } catch (HttpStatusCodeException cause) {
            if (HttpStatus.NOT_FOUND.equals(cause.getStatusCode())) {
                throw new CustomerNotFoundException();
            }
            log.error(new StringBuilder("Unable to Search Customer\n Response:")
                    .append("\n   status: ").append(cause.getStatusCode().value())
                    .append("\n   body: ").append(cause.getResponseBodyAsString())
                    .toString());
            throw new CustomerException("Unable to Search Customer");
        }
    }

}
