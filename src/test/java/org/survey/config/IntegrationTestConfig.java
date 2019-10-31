package org.survey.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.survey.selenium.SeleniumConfig;

@Configuration
@Import({ JpaConfig.class, ApplicationConfig.class, SeleniumConfig.class })
@EnableFeignClients(basePackages = { "org.survey.service.user" })
public class IntegrationTestConfig {
    @Bean
    public String url() {
        return "http://localhost:8082";
    }
}
