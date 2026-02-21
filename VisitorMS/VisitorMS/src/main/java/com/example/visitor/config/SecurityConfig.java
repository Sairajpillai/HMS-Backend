package com.example.visitor.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // @Bean
    // SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    //     // http.authorizeHttpRequests((requests)->requests.requestMatchers("/**").permitAll().anyRequest().authenticated());
    //     // http.csrf(csrf -> csrf.disable());
    //     // return http.build();
    //     http
    //         .csrf(csrf -> csrf.disable())
    //         .authorizeHttpRequests(auth -> auth
    //             .requestMatchers(request -> 
    //                 "SECRET".equals(request.getHeader("X-Secret-Key"))
    //             ).permitAll()
    //             .anyRequest().denyAll()
    //         );

    //     return http.build();
    // }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth

                // ✅ Allow Swagger
                .requestMatchers(
                    "/v3/api-docs/**",
                    "/swagger-ui/**",
                    "/swagger-ui.html"
                ).permitAll()

                .requestMatchers("/actuator/**").permitAll()

                // ✅ Allow requests with X-Secret-Key
                .requestMatchers(request ->
                    "SECRET".equals(request.getHeader("X-Secret-Key"))
                ).permitAll()

                // ❌ Everything else blocked
                .anyRequest().denyAll()
            );

        return http.build();
    }


    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception{
        
        return builder.getAuthenticationManager();
    }
    
}
