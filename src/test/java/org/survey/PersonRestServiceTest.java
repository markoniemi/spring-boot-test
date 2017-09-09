package org.survey;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.survey.config.ApplicationConfig;
import org.survey.domain.Person;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = ApplicationConfig.class))
@Log4j2
public class PersonRestServiceTest {
    TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void get() throws JsonParseException, JsonMappingException, IOException {
        String url = "http://localhost:8080/api/persons";
        ResponseEntity<String> responseString = testRestTemplate.getForEntity(url, String.class);
        Assert.assertNotNull(responseString);
        log.debug("response: {}", responseString.getBody());
        ObjectMapper objectMapper = new ObjectMapper();
        List<Person> persons = objectMapper.readValue(responseString.getBody(), new TypeReference<List<Person>>() {
        });
        log.debug("persons: {}", Arrays.toString(persons.toArray()));
        Assert.assertNotNull(persons);
        Assert.assertEquals(1, persons.size());
    }
}
