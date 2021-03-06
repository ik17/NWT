package com.example.demo;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.controller.KorisnikController;
import com.example.demo.entity.Korisnik;

@Component
public class Reciver {
    private CountDownLatch latch = new CountDownLatch(1);
    
    @Autowired
    KorisnikController kC;
    
   
    
    

    public void receiveMessage(String message) throws Exception {
    	System.out.println("Receiver historije clanka");
        System.out.println("Received <" + message + ">");
        String action = message.substring(0,1);
        String data = message.substring(1);
        System.out.println(action);
        if(action.equals("1")) {
        	//System.out.println(action);
        //kC.createUder(new Korisnik(data), "ROLE_REVIEWER", null);
        	kC.createUder(new Korisnik(data), null);
        }
        else if( action.equals( "2")) {
        	kC.updateUser(Long.parseLong(data.substring(0,1),10), new Korisnik(data.substring(1)),"ROLE_REVIEWER", null);
        }
        else  {
        	//kC.deleteCategory(Long.parseLong(data,10));
        	System.out.println(Long.parseLong(data,10));
        	kC.deleteCategory(Long.parseLong(data,10), "ROLE_REVIEWER");
        	
        }
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}