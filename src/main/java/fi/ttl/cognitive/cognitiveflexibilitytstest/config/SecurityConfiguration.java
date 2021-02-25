package fi.ttl.cognitive.cognitiveflexibilitytstest.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling()
                .and()
                .authorizeRequests()
                .antMatchers("/manage/**").permitAll()
                .antMatchers("/", "/index.html", "/static/**", "/app/login", "/app/auth/*", "/app/form*", "/app/test").permitAll()
                .anyRequest().authenticated()
                .and()
                .rememberMe()
                .and()
                .csrf().disable();
    }
}