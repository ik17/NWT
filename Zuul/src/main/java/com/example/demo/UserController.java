package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.LoginUser;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceImpl userService;
    @PostMapping("/signup")
    public User saveUser(@RequestBody LoginUser user){
      return userService.save(user);
    }
    // Other methods
}