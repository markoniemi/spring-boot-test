package org.survey;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.survey.config.ApplicationConfig;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = ApplicationConfig.class))
@Log4j2
public class HomeControllerTest {
    TestRestTemplate testRestTemplate = new TestRestTemplate();

    @Test
    public void home() {
        ResponseEntity<String> entity = testRestTemplate.getForEntity("http://localhost:8080/", String.class);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertTrue(entity.getBody().contains("Hello World"));
    }
    @Test
    public void index() {
        ResponseEntity<String> entity = testRestTemplate.getForEntity("http://localhost:8080/index", String.class);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertTrue(entity.getBody().contains("index"));
    }
}
