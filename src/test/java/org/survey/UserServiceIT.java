package org.survey;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.survey.config.IntegrationTestConfig;
import org.survey.model.user.User;
import org.survey.service.user.UserClient;
import org.survey.service.user.UserService;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.log4j.Log4j2;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = IntegrationTestConfig.class))
@Log4j2
public class UserServiceIT {
    private TestRestTemplate testRestTemplate = new TestRestTemplate();
    @Resource
    private String url;
    @Resource
    private UserClient userClient;

    public UserService getUserWsClient() throws MalformedURLException {
        URL wsdlURL = new URL("http://localhost:8082/api/soap/users?wsdl");
        QName qname = new QName("http://user.service.survey.org/", "UserService");
        Service service = Service.create(wsdlURL, qname);
        return service.getPort(UserService.class);
    }

    @Test
    public void getUsersRest() throws JsonParseException, JsonMappingException, IOException {
        ResponseEntity<String> responseString = testRestTemplate.getForEntity(url + "/api/rest/users", String.class);
        Assert.assertNotNull(responseString);
        List<User> users = parseResponse(responseString);
        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.size());
    }
    @Test
    public void getUsersFeign() throws JsonParseException, JsonMappingException, IOException {
        User[] users = userClient.findAll();
        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.length);
    }

    @Test
    public void getUsersWs() throws JsonParseException, JsonMappingException, IOException {
        UserService userService = getUserWsClient();
        User[] users = userService.findAll();
        Assert.assertNotNull(users);
        Assert.assertEquals(1, users.length);
    }

    private List<User> parseResponse(ResponseEntity<String> responseString)
            throws IOException, JsonParseException, JsonMappingException {
        return new ObjectMapper().readValue(responseString.getBody(), new TypeReference<List<User>>() {
        });
    }
}
