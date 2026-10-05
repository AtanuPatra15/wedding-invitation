package com.wedinvitation.wedding_backend;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())

            .cors(Customizer.withDefaults())

            .authorizeHttpRequests(auth -> auth

                .requestMatchers(HttpMethod.POST, "/api/rsvp").permitAll()

                .requestMatchers(HttpMethod.GET, "/api/rsvp").authenticated()

                .anyRequest().permitAll()
            )

            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}