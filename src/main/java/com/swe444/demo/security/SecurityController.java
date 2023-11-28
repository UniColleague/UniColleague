package com.swe444.demo.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityController {


    @Bean
    public UserDetailsManager detailsManager(DataSource dataSource){

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id,pw,active from user where user_id=?"
        );

        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id,role from roles where user_id=?"
        );


        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        http.authorizeHttpRequests(configurer -> configurer
                .requestMatchers("main/showRegisterForm/**").permitAll()
                .requestMatchers("main/forgot-password/**").permitAll()
                .requestMatchers("main/reset-password/**").permitAll()
                .requestMatchers("main/reset-password2/**").permitAll()
                .requestMatchers("main/forgot-password-sc/**").permitAll()
                .requestMatchers("main/saveUser/**").permitAll()
                .anyRequest().authenticated()).formLogin(form -> form.loginPage("/showLoginForm")
                .loginProcessingUrl("/authenticateTheUser")
                .permitAll()).logout(logout -> logout.permitAll());


        return http.build();
    }
}
