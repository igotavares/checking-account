package br.com.ibeans.checkingaccount.port.adapter.resource;

import br.com.ibeans.checkingaccount.AbstractIT;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

@Getter
@AutoConfigureJsonTesters
@SpringBootTest
@AutoConfigureMockMvc
public abstract class AbstractResourceIT extends AbstractIT {

    @Autowired
    private MockMvc mvcMock;

}
