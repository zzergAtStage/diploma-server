package com.zergatstageg.s02cruddemo.ssl.config;

import com.zergatstageg.s02cruddemo.ssl.repository.UserRepository;
import com.zergatstageg.s02cruddemo.ssl.services.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.ArrayList;
import java.util.List;

import static com.zergatstageg.s02cruddemo.ssl.domain.UserRole.ADMIN;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final UserService userService;

    public WebSecurityConfig(UserService userService) {
        this.userService = userService;
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/",
                                        "ssl/public/**",
                                        "/css/**",
                                        "/img/**").permitAll()
                                .requestMatchers("/private-data","/console","/exports","/users").hasRole(ADMIN.name())
                                .anyRequest().authenticated());

        http.formLogin((form) -> form.loginPage("/login").permitAll()
                .defaultSuccessUrl("/user-details"));
        //http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.logout(LogoutConfigurer::permitAll);
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    //only for testing!
    @Bean
    public UserDetailsService userDetailsService() {
        List<UserDetails> users = new ArrayList<>();
        users.add(User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build());
        users.add(User.withDefaultPasswordEncoder()
                .username("admin")
                .password("qaz123")
                .roles("USER","ADMIN")
                .build());

        return new InMemoryUserDetailsManager(users);
    }
}
