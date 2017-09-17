package org.survey.config;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;
import org.survey.domain.Person;
import org.survey.repository.PersonRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DatabaseInitBean implements InitializingBean {
    @Resource
    private PersonRepository personRepository;

    @Override
    public void afterPropertiesSet() throws Exception {
        log.debug("afterPropertiesSet");
        personRepository.save(new Person("email", "firstName", "lastName"));
    }
}
