package com.example.demo;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Entities.Korisnik;

import com.example.demo.Controller.KorisnikController;
import com.example.demo.Entities.Korisnik;

@Component
public class Subscriber {
	@Autowired
	KorisnikController korisnikController;
	
	@RabbitListener(queues="spring-boot-7")
	public void receivedMessage(String message) throws Exception {
		System.out.println("Received message " + message);
		 String action = message.substring(0,1);
	        String data = message.substring(1);
	        System.out.println(action);
	        if(action.equals("1")) {
	        	//System.out.println(action);
	        	//korisnikController.createUser(new Korisnik(data), "ROLE_AUTOR",null);
	        	korisnikController.createUser(new Korisnik(data), null);
	        }
	        else if( action.equals( "2")) {
	        	korisnikController.updateKorisnik(Long.parseLong(data.substring(0,1),10), new Korisnik(data.substring(1)),"ROLE_AUTOR", null);
	        }
	        else  {
	        	//kC.deleteCategory(Long.parseLong(data,10));
	        	System.out.println(Long.parseLong(data,10));
	        	korisnikController.deleteUser(Long.parseLong(data,10),"ROLE_AUTOR");
	        	
	        }
	}
}

