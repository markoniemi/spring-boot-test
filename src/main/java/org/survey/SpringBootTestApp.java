package org.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.survey.config.ApplicationConfig;

//@SpringBootApplication
//@Configuration 
//@EnableAutoConfiguration 
//@ComponentScan
@EnableWebMvc
public class SpringBootTestApp {
    
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
}
