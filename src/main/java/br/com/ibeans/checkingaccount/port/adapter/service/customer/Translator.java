package br.com.ibeans.checkingaccount.port.adapter.service.customer;

public interface Translator<T>  {

    T to(String body);

}
