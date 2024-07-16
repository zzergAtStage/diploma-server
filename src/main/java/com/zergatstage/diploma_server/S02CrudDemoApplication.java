package com.zergatstage.diploma_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootApplication
@EnableGlobalAuthentication
public class S02CrudDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(S02CrudDemoApplication.class, args);
		User.UserBuilder users = User.withDefaultPasswordEncoder();
		UserDetails user = users
				.username("user")
				.password("password")
				.roles("USER")
				.build();
		UserDetails admin = users
				.username("admin")
				.password("password")
				.roles("USER","ADMIN")
				.build();
	}
}
