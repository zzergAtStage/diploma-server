package com.zergatstageg.s02cruddemo.services;

import com.zergatstageg.s02cruddemo.domain.User;
import com.zergatstageg.s02cruddemo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public List<User> findAll(){
        return repo.findAll();
    }

    public void saveUser(User user){
        repo.save(user);
    }

    public void updateUser(User user){
        repo.update(user);
    }

    public User findUserById(int id) {
       return repo.findById(id);
    }

    public void deleteUserById(int id){
        repo.deleteById(id);
    }
}
