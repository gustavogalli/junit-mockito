package com.valdir.api.resource;

import com.valdir.api.domain.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserResource {

    @GetMapping("/{id}")
    public ResponseEntity<User> findById(){
        return ResponseEntity.ok().body(new User(1, "Gustavo", "gus@gmail.com", "123"));
    }

}
