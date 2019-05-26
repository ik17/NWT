package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
	public User findByUsername(String username);
	
	@Query(value = "SELECT * FROM user WHERE username = ?1 ", 
			  nativeQuery = true)	
	public User findOne(String username);
}
