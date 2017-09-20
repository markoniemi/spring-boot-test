package org.survey.config;

import javax.annotation.Resource;
import javax.xml.ws.Endpoint;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.survey.service.user.UserService;

@Configuration
public class WebServiceConfig {
    @Resource
    private UserService userService;
    @Resource
    private Bus bus;

    @Bean
    public Endpoint endpoint() {
        EndpointImpl endpoint = new EndpointImpl(bus, userService);
        endpoint.setAddress("/soap/users");
        endpoint.publish("/soap/users");
        return endpoint;
    }
}