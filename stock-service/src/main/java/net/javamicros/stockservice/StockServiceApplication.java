package net.javamicros.stockservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class StockServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
	}

}
