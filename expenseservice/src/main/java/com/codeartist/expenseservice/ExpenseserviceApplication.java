package com.codeartist.expenseservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ExpenseserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExpenseserviceApplication.class, args);
	}

}
