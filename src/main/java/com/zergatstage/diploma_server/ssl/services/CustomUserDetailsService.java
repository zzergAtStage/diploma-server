package com.zergatstage.diploma_server.ssl.services;

import com.zergatstage.diploma_server.ssl.domain.CustomUserDetails;
import com.zergatstage.diploma_server.ssl.domain.User;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;
    @Autowired
    private final BCryptPasswordEncoder passwordEncoder;
    public CustomUserDetailsService(UserService userService, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    private final Map<String, CustomUserDetails> userRegistry = new HashMap<>();

    @PostConstruct
    public void init() {
//        userRegistry.put("user", new CustomUserDetails.Builder().withFirstName("Mark")
//                .withLastName("Johnson")
//                .withEmail("mark.johnson@email.com")
//                .withUsername("user")
//                .withPassword(passwordEncoder().encode("password"))
//                .withAuthorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")))
//                .build());
        userRegistry.put("admin", new CustomUserDetails.Builder().withFirstName("James")
                .withLastName("Davis")
                .withEmail("james.davis@email.com")
                .withUsername("admin")
                .withPassword(passwordEncoder.encode("password"))
                .withAuthorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                .build());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetails userDetails = userRegistry.get(username); //local cache
        if (userDetails == null) {
            //If userDetails didn't fount search in DB
            userDetails = getUserDetailsFromRepository(username);
            userRegistry.put(userDetails.getUsername(), userDetails);
        }
        return userDetails;
    }

    private CustomUserDetails getUserDetailsFromRepository(String username) {
        Optional<User> user = userService.findByUserName(username);
        if (user.isPresent()) {
            return new CustomUserDetails.Builder().withFirstName(user.get().getFirstName())
                    .withLastName(user.get().getLastName())
                    .withEmail(user.get().getUserEmail())
                    .withUsername(user.get().getUserName())
                    .withPassword(user.get().getPassword()) //already encoded!
                    .withAuthorities(Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    .build();
        } else throw new UsernameNotFoundException("User not found in DB");
    }
}