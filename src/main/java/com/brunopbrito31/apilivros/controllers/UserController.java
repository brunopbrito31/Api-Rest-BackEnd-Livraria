package com.brunopbrito31.apilivros.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import com.brunopbrito31.apilivros.models.entities.User;
import com.brunopbrito31.apilivros.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAll(){
        List<User> users = userService.getAllusers();
        if(users.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getByID(@PathVariable Long id){
        Optional<User> searschedUser = userService.getuserById(id);
        if(!searschedUser.isPresent()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(searschedUser.get());
    }

    @PostMapping
    public ResponseEntity<User> insert(@RequestBody User user){

        user = userService.insertuser(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}")
                .buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        userService.deleteuser(id);
        return ResponseEntity.noContent().build();
    }
    
}
