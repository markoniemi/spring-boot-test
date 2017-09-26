package org.survey.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.formLogin().loginPage("/login").loginProcessingUrl("/j_spring_security_check")
                .usernameParameter("j_username").passwordParameter("j_password").failureUrl("/login?loginError=true").permitAll();
        http.logout().logoutUrl("/j_spring_security_logout").logoutSuccessUrl("/login").permitAll();
        http.authorizeRequests().antMatchers("/", "/home", "/api/**").permitAll().anyRequest().authenticated();
//        .and().formLogin()
//                .loginPage("/login").permitAll().and().logout().permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }
}