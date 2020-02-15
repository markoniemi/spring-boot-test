package org.survey.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import lombok.extern.log4j.Log4j2;

@Configuration
@Profile("disableSecurity")
@Log4j2
public class TestSecurityConfig extends WebSecurityConfig {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("ServiceSecurityConfig.configure");
        super.configure(http);
        http.authorizeRequests().antMatchers("/api/**").permitAll();
    }
}
