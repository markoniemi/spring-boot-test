package org.survey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.survey.config.ApplicationConfig;

//@SpringBootApplication
//@Configuration 
//@EnableAutoConfiguration 
//@ComponentScan
//@EnableWebMvc
public class SpringBootTestApp {
    
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(SampleWebJspApplication.class);
//    }    
}
