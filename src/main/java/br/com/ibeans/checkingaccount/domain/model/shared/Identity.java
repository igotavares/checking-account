package br.com.ibeans.checkingaccount.domain.model.shared;

import java.io.Serializable;

public interface Identity extends Serializable {

    String getId();

    void setId(String id);

}
