package br.com.ibeans.checkingaccount.port.adapter.persistence;

import br.com.ibeans.checkingaccount.domain.model.shared.IdentityGenerator;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class IdentityGeneratorImpl implements IdentityGenerator {

    @Override
    public String next() {
        return UUID.randomUUID().toString();
    }

}
