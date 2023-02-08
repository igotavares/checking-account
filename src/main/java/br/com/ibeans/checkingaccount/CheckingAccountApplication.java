package br.com.ibeans.checkingaccount;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class CheckingAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(CheckingAccountApplication.class, args);
	}

}
