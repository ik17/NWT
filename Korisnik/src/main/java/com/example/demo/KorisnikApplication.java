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
	
	
	
	
	public static void main(String[] args) {
		SpringApplication.run(KorisnikApplication.class, args);
		System.out.println("INSIDEE");
	}
	
	@Override
    public void run(String... arg0) throws Exception {
		Date d = new Date();
		KorisnikPodaci kp = new KorisnikPodaci("ime", "prezime", "nesto o njemu", d);
		UlogaKorisnik uk = new UlogaKorisnik("uloga");
		Korisnik k = new Korisnik("username", "password", kp, uk);
		podaciRepository.save(kp);
		ulogaRepository.save(uk);
		korisnikRepository.save(k);
	}

}

