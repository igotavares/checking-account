package br.com.ibeans.checkingaccount.port.adapter.resource.converter;

public interface Converter<ENTITY, DATA> {

    DATA toData(ENTITY entity);

    ENTITY toEntity(DATA data);

}
