package org.survey.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@EnableAutoConfiguration
@Configuration
@ComponentScan(basePackages = "org.survey")
@EntityScan(basePackages = "org.survey.domain")
@Import({ JpaConfig.class, TomcatConfig.class, WebMvcConfig.class, RestConfig.class })
public class ApplicationConfig {
}