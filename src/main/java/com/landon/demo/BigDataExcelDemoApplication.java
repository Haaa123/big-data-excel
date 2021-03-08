package com.landon.demo;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author SSP
 */
@SpringBootApplication(scanBasePackages = {"com.landon.demo"})
@EntityScan(basePackages = {"com.landon.demo.**.entity"})
@EnableJpaRepositories(basePackages = {"com.landon.demo.**.repository"})
@EnableAsync
public class BigDataExcelDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(BigDataExcelDemoApplication.class, args);
    }


    /**
     * 多数据源源配置
     * @return HikariDataSource 资源
     */
    @Primary
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari.datasource")
    public HikariDataSource dataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .driverClassName("com.mysql.cj.jdbc.Driver")
                .url("jdbc:mysql://localhost:3306/big_data_excel_local?serverTimezone=Asia/Shanghai&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true")
                .username("root")
                .password("admin")
                .build();
    }

}
