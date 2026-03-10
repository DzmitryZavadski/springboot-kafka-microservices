package net.javamicros.stockservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication(
        scanBasePackages = "net.javamicros"
//        exclude = {DataSourceAutoConfiguration.class}
)
//@EnableJpaRepositories(basePackages = "net.javamicros.stockservice.repository")
@EntityScan(basePackages = "net.javamicros.basedomains.dto")
public class StockServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(StockServiceApplication.class, args);
	}
}
