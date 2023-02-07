package br.com.ibeans.checkingaccount;


import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import javax.transaction.Transactional;

@Getter
@AutoConfigureTestEntityManager
@Transactional
public abstract class AbstractIT  {

    @Autowired
    private TestEntityManager entityManager;

}
