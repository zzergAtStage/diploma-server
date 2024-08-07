package com.zergatstage.diploma_server.ssl.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

//@Configuration
//@EnableWebSecurity
public class _WebSecurityConfig {

    @Bean
    public SecurityFilterChain webFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {

        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);
        http.authorizeHttpRequests(
                authorizationManagerRequestMatcherRegistry -> authorizationManagerRequestMatcherRegistry
                        .requestMatchers(
                                mvcMatcherBuilder
                                        .pattern("/users")).permitAll()
                        .anyRequest().authenticated());
        http.formLogin((form) -> form.loginPage("/login").permitAll());
        http.logout(LogoutConfigurer::permitAll);
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
