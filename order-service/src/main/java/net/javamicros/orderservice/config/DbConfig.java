package net.javamicros.orderservice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@Getter
@Setter
@ConfigurationProperties(prefix = "database")
@EntityScan(basePackages = "net.javamicros.basedomains.dto") // Указываем путь к сущностям
@EnableJpaRepositories(basePackages = "net.javamicros.orderservice.repository") // Указываем путь к репозиториям
public class DbConfig {

    private String url;
    private String username;
    private String password;
    private String driverClassName;

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName(driverClassName)
                .build();
    }
}
