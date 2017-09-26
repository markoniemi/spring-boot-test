package org.survey;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Ignore;
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

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = ApplicationConfig.class))
@Slf4j
@Ignore("implement security")
// TODO https://github.com/mraible/java-webapp-security-examples/blob/master/spring-security/src/test/java/security/WebSecurityTests.java
public class UsersControllerIT {
    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    @Resource
    private String url;

    @Test
    public void users() {
        ResponseEntity<String> entity = testRestTemplate.getForEntity(url+"/user/users", String.class);
        Assert.assertEquals(HttpStatus.OK, entity.getStatusCode());
        Assert.assertTrue(entity.getBody().contains("<td id=\"username\">username</td>"));
        Assert.assertTrue(entity.getBody().contains("<td id=\"role\">Admin</td>"));
    }
}
