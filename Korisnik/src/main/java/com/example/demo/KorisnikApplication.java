package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import com.example.demo.Controllers.KorisnikController;
import com.example.demo.Entities.Korisnik;
import com.example.demo.Entities.KorisnikPodaci;
import com.example.demo.Entities.UlogaKorisnik;
import com.example.demo.Repositories.KorisnikPodaciRepository;
import com.example.demo.Repositories.KorisnikRepository;
import com.example.demo.Repositories.UlogaKorisnikRepository;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Date;
import java.util.stream.Stream;
@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration
public class KorisnikApplication  implements CommandLineRunner{
@Autowired 
KorisnikPodaciRepository podaciRepository;
@Autowired 
KorisnikRepository korisnikRepository;
@Autowired
UlogaKorisnikRepository ulogaRepository;
@Autowired
KorisnikController kC;
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(KorisnikApplication.class, args);
		System.out.println("INSIDEE");
	}
	
	@Override
    public void run(String... arg0) throws Exception {
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
		kC.updateKorisnik(k.getId(), new Korisnik("KomunikacijaUpdate", "password2", kp2, uk3), null);
		System.out.println(k.getUsername());
		 kC.deleteKorisnik(k.getId());
		
		
	}

}

