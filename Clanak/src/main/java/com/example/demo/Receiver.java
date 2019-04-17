package com.example.demo;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.Controller.KorisnikController;
import com.example.demo.Entities.Korisnik;


@Component
public class Receiver {
	@Autowired
	KorisnikController korisnikController;
	private CountDownLatch latch = new CountDownLatch(1);
	public void receiveMessage(String message) throws Exception {
		System.out.println("Receiver clanak");
		System.out.println("Received <" + message + ">");
        String action = message.substring(0,1);
        String data = message.substring(1);
        System.out.println(action);
        if(action.equals("1")) {
        	//System.out.println(action);
        	korisnikController.createUser(new Korisnik(data), null);
        }
        else if( action.equals( "2")) {
        	korisnikController.updateKorisnik(Long.parseLong(data.substring(0,1),10), new Korisnik(data.substring(1)), null);
        }
        else  {
        	//kC.deleteCategory(Long.parseLong(data,10));
        	System.out.println(Long.parseLong(data,10));
        	korisnikController.deleteUser(Long.parseLong(data,10));
        	
        }
        latch.countDown();
	}
	public CountDownLatch getLatch() {
        return latch;
    }
}
