package com.example.demo;

import java.util.Date;
import java.util.concurrent.TimeUnit;
import com.example.demo.Controllers.KorisnikController;
import com.example.demo.Entities.Korisnik;
import com.example.demo.Entities.KorisnikPodaci;
import com.example.demo.Entities.UlogaKorisnik;
import com.example.demo.Repositories.KorisnikPodaciRepository;
import com.example.demo.Repositories.KorisnikRepository;
import com.example.demo.Repositories.UlogaKorisnikRepository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.Controllers.KorisnikController;
import com.example.demo.Repositories.KorisnikPodaciRepository;
import com.example.demo.Repositories.KorisnikRepository;
import com.example.demo.Repositories.UlogaKorisnikRepository;

@Component
public class Runner implements CommandLineRunner{
	@Autowired 
	KorisnikPodaciRepository podaciRepository;
	@Autowired 
	KorisnikRepository korisnikRepository;
	@Autowired
	UlogaKorisnikRepository ulogaRepository;
	@Autowired
	KorisnikController kC;
	private final RabbitTemplate rabbitTemplate;
   // private final Reciver receiver;

    public Runner(/*Reciver receiver,*/ RabbitTemplate rabbitTemplate) {
      //  this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Sending message...");
        Date d = new Date();
		KorisnikPodaci kp1 = new KorisnikPodaci("ime", "prezime", "nesto o njemu bla bla bla bla bla bla", d);
		KorisnikPodaci kp2 = new KorisnikPodaci("jana", "dva", "jana dva kazu ista je ko ja haha", d);
		
		UlogaKorisnik uk1 = new UlogaKorisnik("uloga1");
		UlogaKorisnik uk2 = new UlogaKorisnik("uloga2");
		UlogaKorisnik uk3 = new UlogaKorisnik("uloga3");
		
		Korisnik k1 = new Korisnik("Komunikacija", "password1", kp1, uk1);
		Korisnik k2 = new Korisnik("username2", "password2", kp2, uk3);
		
		podaciRepository.save(kp1);
		podaciRepository.save(kp2);
		ulogaRepository.save(uk1);
		ulogaRepository.save(uk2);
		ulogaRepository.save(uk3);
		//korisnikRepository.save(k1);
		korisnikRepository.save(k2);
		
		Korisnik k = kC.createKorisnik(k1, null);
		System.out.println(k.getUsername());
		//kC.updateKorisnik(k.getId(), new Korisnik("KomunikacijaUpdate", "password2", kp2, uk3), null);
		System.out.println(k.getUsername());
		// kC.deleteKorisnik(k.getId());
        rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.HistorijaClanak.korisnik", "IMEKOMUNIKACIJA");
        //receiver.getLatch().await(10000, TimeUnit.MILLISECONDS);
    }

}
