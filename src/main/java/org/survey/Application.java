package org.survey;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
    @Resource
    private PersonRepository personRepository;
    public static void main(String[] args) {
        SpringApplication.run(ApplicationConfig.class, args);
    }
    @PostConstruct
    public void init() {
        personRepository.save(new Person("email", "firstName", "lastName"));
    }
}
