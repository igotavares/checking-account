package br.com.ibeans.checkingaccount.port.adapter.service.customer;

import br.com.ibeans.checkingaccount.domain.model.customer.Customer;

public interface CustomerAdapter {

    Customer toCustomer(String documentNumber);

}
