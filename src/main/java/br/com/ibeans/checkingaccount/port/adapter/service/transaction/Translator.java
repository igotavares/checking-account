package br.com.ibeans.checkingaccount.port.adapter.service.transaction;

public interface Translator<T> {

    String from(T entity);

}
