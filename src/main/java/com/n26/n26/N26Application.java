package com.n26.n26;
import com.n26.n26.model.Transaction;
//import com.n26.n26.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

//@EnableJpaRepositories(basePackageClasses = TransactionRepository.class)
@SpringBootApplication
public class N26Application {

	public static void main(String[] args) {
		SpringApplication.run(N26Application.class, args);
	}
	/*@Bean
	CommandLineRunner init (TransactionRepository transactionRepository) {
		return (evt) -> Arrays.asList(120.3,12.3,10.3,121.3,122.3)
				.forEach( a -> {
					Transaction transaction = transactionRepository.save(new Transaction(a, new Date().getTime()));
				});
	}*/
}
