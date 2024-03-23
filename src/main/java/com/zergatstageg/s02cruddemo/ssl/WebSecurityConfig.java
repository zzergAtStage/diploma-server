package com.zergatstageg.s02cruddemo.ssl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import static com.zergatstageg.s02cruddemo.ssl.domain.UserRole.ADMIN;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/",
                                        "ssl/public/**",
                                        "/css/**",
                                        "/img/**").permitAll()
                                .requestMatchers("/private-data","/console","/exports").hasRole(ADMIN.name())
                                .anyRequest().authenticated());

        http.formLogin((form) -> form.loginPage("/login").permitAll()
                .successForwardUrl("/user-details"));
        //http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.logout(LogoutConfigurer::permitAll);
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
