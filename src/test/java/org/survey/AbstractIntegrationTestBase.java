package org.survey;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringRunner;
import org.survey.config.IntegrationTestConfig;

import lombok.extern.log4j.Log4j2;

/**
 * Base class for integration tests, enables running multiple tests
 * with @SpringBootTest
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SpringBootTestApp.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextHierarchy(@ContextConfiguration(classes = IntegrationTestConfig.class))
@Log4j2
@ActiveProfiles(profiles = { "h2" })
public class AbstractIntegrationTestBase {
    @Autowired
    protected Environment environment;
    @Autowired
    protected DiscoveryClient discoveryClient;

    @Test
    public void dummy() {
        Assert.assertTrue(true);
    }

    protected String get(String url, MediaType mediaType) {
        HttpHeaders headers = new HttpHeaders();
        if (mediaType != null) {
            headers.setAccept(Collections.singletonList(mediaType));
        }
        ResponseEntity<String> response = new TestRestTemplate().exchange(url, HttpMethod.GET,
                new HttpEntity<>(headers), String.class);
        log.debug(response.getBody());
        Assert.assertTrue(response.getStatusCode() == HttpStatus.OK);
        return response.getBody();
    }

    protected boolean isCloudConfigEnabled() {
        return !Arrays.asList(environment.getActiveProfiles()).contains("local");
    }

    @SuppressWarnings("squid:S2925")
    protected void waitForServiceRegistration() throws InterruptedException {
        while (discoveryClient.getInstances("user-repository").isEmpty()) {
            Thread.sleep(1000);
            log.info(discoveryClient.getServices());
        }
    }
}
