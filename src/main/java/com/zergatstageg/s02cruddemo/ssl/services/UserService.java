package com.zergatstageg.s02cruddemo.ssl.services;

import com.zergatstageg.s02cruddemo.ssl.domain.User;
import com.zergatstageg.s02cruddemo.ssl.repository._UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    //private final UserRepository repo;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private final _UserRepository repo;
    public UserService( _UserRepository repo) {

        this.repo = repo;
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    public void saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        repo.save(user);
    }

    public void updateUser(User user) {
        repo.save(user);
    }

    public Optional<User> findUserById(int id) {
        return repo.findById(id);
    }

    public Optional<User> findByUserName(String userName) {
        return repo.findByFirstName(userName);
    }

    public void deleteUserById(int id) {
        repo.deleteById(id);
    }
}
