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
        System.out.println("Received <" + message + ">");
        kC.createUder(new Korisnik(message), null);
        latch.countDown();
    }

    public CountDownLatch getLatch() {
        return latch;
    }
}