package com.example.demo;



import java.util.Date;
import java.util.concurrent.TimeUnit;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.models.LoginUser;


@Component
public class Runner implements CommandLineRunner{
	@Autowired 
	UserServiceImpl us;



    public Runner(/*Reciver receiver,*/ ) {
      //  this.receiver = receiver;
       
    }

    @Override
    public void run(String... args) throws Exception {
        //System.out.println("Sending message...");
    	LoginUser lu = new LoginUser("Hanna", "hanna", 1);
    	us.save(lu);
         }

}