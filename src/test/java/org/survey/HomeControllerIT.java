package org.survey;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
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
import org.springframework.web.util.UriComponentsBuilder;
import org.survey.config.ApplicationConfig;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = ApplicationConfig.class))
@Slf4j
public class HomeControllerIT {
    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    @Resource
    private String url;

    @Test
    public void home() {
        ResponseEntity<String> entity = testRestTemplate.getForEntity(url, String.class);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertTrue(entity.getBody().contains("Hello World"));
    }

    @Test
    public void index() {
        ResponseEntity<String> entity = testRestTemplate.getForEntity(url + "/index", String.class);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertTrue(entity.getBody().contains("index"));
    }
}
