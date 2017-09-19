package org.survey;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

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
import org.survey.model.user.User;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = ApplicationConfig.class))
@Slf4j
// TODO https://www.javacodegeeks.com/2015/03/spring-boot-integration-testing-with-selenium.html
public class UserServiceIT {
    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    @Resource
    private String url;

    @Test
    public void getPersons() throws JsonParseException, JsonMappingException, IOException {
        ResponseEntity<String> responseString = testRestTemplate.getForEntity(url + "/api/users", String.class);
        Assert.assertNotNull(responseString);
        List<User> users = parseResponse(responseString);
        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.size());
    }

    private List<User> parseResponse(ResponseEntity<String> responseString)
            throws IOException, JsonParseException, JsonMappingException {
        return new ObjectMapper().readValue(responseString.getBody(), new TypeReference<List<User>>() {
        });
    }
}
