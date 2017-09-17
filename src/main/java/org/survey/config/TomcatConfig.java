package org.survey.config;

import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.util.UriComponentsBuilder;

@Configuration
public class TomcatConfig {
    @Bean
    public int port() {
        return 8082;
    }
    @Bean @DependsOn("port") 
    public EmbeddedServletContainerFactory servletContainer() {
        TomcatEmbeddedServletContainerFactory factory = new TomcatEmbeddedServletContainerFactory();
        factory.setPort(port());
        return factory;
    }
    // TODO move to some test config?
    @Bean @DependsOn("servletContainer")
    public String url() {
        return UriComponentsBuilder.newInstance().scheme("http").host("localhost").port(port()).build().toString();
    }
}
