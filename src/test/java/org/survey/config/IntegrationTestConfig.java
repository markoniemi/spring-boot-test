package org.survey.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.survey.selenium.SeleniumConfig;
import org.survey.service.user.UserService;
import org.survey.service.user.UserServiceImpl;

@Configuration
// @ComponentScan(basePackages = "org.survey")
@Import({ JpaConfig.class, ApplicationConfig.class, SeleniumConfig.class })
public class IntegrationTestConfig {
    @Bean("userService")
    public UserService getUserService() {
        return new UserServiceImpl();
    }

    @Bean
    public String url() {
        return "http://localhost:8082/survey-spring-web";
    }
}
