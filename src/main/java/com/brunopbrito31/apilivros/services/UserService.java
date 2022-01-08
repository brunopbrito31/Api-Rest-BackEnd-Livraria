package com.brunopbrito31.apilivros.services;

import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.User;
import com.brunopbrito31.apilivros.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllusers() {
        return userRepository.findAll();
    }

    public Optional<User> getuserById(Long id){
        return userRepository.findById(id);
    }

    public User insertuser(User user){
        user = userRepository.save(user);
        return user;
    }

    public void deleteuser(Long id){
        userRepository.deleteById(id);
    }
}
