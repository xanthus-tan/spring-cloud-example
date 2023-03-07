package com.xanthus;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAdminServer
public class SpringBootAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootAdminApplication.class, args);
    }
//    @Configuration
//    public static class SecurityConfig extends WebSecurityConfigurerAdapter {
//        @Override
//        protected void configure(HttpSecurity http) throws Exception {
//            http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").permitAll();
//            http.logout().logoutUrl("/logout");
//            http.csrf().disable();
//            http.authorizeRequests().antMatchers("/login.html", "/**/*.css", "/img/**", "/third-party/**").permitAll();
//            http.authorizeRequests().antMatchers("/**").authenticated();
//            http.httpBasic();
//        }
//    }
}