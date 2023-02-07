package br.com.ibeans.checkingaccount.port.adapter.service.customer.impl;

import br.com.ibeans.checkingaccount.domain.model.customer.Customer;
import br.com.ibeans.checkingaccount.domain.model.customer.CustomerService;
import br.com.ibeans.checkingaccount.port.adapter.service.customer.CustomerAdapter;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TranslatingCustomerServiceImpl implements CustomerService {

    private CustomerAdapter customerAdapter;

    @Override
    public Customer customerFrom(String documentNumber) {
        return customerAdapter.toCustomer(documentNumber);
    }

}
