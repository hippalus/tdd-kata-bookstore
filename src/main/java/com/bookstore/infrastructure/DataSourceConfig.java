package com.bookstore.infrastructure;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.concurrent.TimeUnit;

@Configuration
@EnableJpaRepositories(basePackages = {"com.bookstore.infrastructure.repository"})
@EnableTransactionManagement
@ConfigurationProperties(prefix = "spring.datasource")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataSourceConfig {
    private String url;

    private String username;

    private String password;

    private String driverClassName;

    private String poolName;


    @Bean
    @Qualifier("dataSource")
    public DataSource dataSource() {
        return new HikariDataSource(hikariConfig());
    }

    private HikariConfig hikariConfig() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(driverClassName);
        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setPoolName(poolName);
        config.setMaximumPoolSize(20);
        config.setMinimumIdle(2);
        config.setIdleTimeout(TimeUnit.SECONDS.toMillis(30));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return config;
    }

}
