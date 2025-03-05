package com.aab.insurance.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public UserDetailsManager userDetailsManager() {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())  // Fix for H2 console
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/customers", "/api/customers/**").hasRole("CUSTOMER_MANAGER") // Requires ROLE_CUSTOMER_MANAGER
                        .requestMatchers("/api/claims", "/api/claims/**").hasRole("CLAIM_MANAGER") // Requires ROLE_CLAIM_MANAGER
                        .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll() // H2 console accessible
                        .anyRequest().authenticated()
                )
                .formLogin(formLogin -> formLogin.permitAll()) // Enables form login
                .logout(logout -> logout.permitAll())
                .headers(headers -> headers.frameOptions().sameOrigin()); // Enables logout

        return httpSecurity.build();
    }


}
