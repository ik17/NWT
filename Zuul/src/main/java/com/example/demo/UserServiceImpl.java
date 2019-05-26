package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.models.LoginUser;

//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Collections.emptyList;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {
   @Autowired
   private UserRepository userDao;   
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;      
  // @Override
   public User save(LoginUser user) {
         User newUser = new User(user.getUsername(),passwordEncoder.encode(user.getPassword()),1);
         //newUser.setUsername(user.getUsername());
         //newUser.setPassword(passwordEncoder.encode(user.getPassword()));
         return userDao.save(newUser);
    }
   public User findOne(String username) {
	   return userDao.findOne(username);
   }
   public UserDetails loadUserByUsername(String userId) throws
               UsernameNotFoundException {
         User user = userDao.findByUsername(userId);
         if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
         }
       //TODO: Get user by username from users microservice, and change emptyList() with user role
         return new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), emptyList());
         }
  // Other service methods
}