package com.example.demo;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import com.example.demo.Entities.Autor;
import com.example.demo.Entities.Clanak;
import com.example.demo.Entities.Kategorija;
import com.example.demo.Entities.Komentar;
import com.example.demo.Entities.Korisnik;
import com.example.demo.Repositories.AutorRepository;
import com.example.demo.Repositories.ClanakRepository;
import com.example.demo.Repositories.KategorijaRepository;
import com.example.demo.Repositories.KomentarRepository;
import com.example.demo.Repositories.KorisnikRepository;

@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration
public class ClanakApplication implements CommandLineRunner {
@Autowired 
DataSource dataSource;
@Autowired
AutorRepository autorRepository;
@Autowired
ClanakRepository clanakRepository;
@Autowired
KategorijaRepository kategorijaRepository;
@Autowired
KomentarRepository komentarRepository;
@Autowired
KorisnikRepository korisnikRepository;

	public static void main(String[] args) {
		SpringApplication.run(ClanakApplication.class, args);
	}
	
	@Override
    public void run(String... arg0) throws Exception {
		Korisnik k3 = new Korisnik("Korisnik");
		Kategorija k = new Kategorija("kategorija");
		Clanak c = new Clanak("naziv", k, true, k3);
		Autor a = new Autor(c, k3);
		
		Komentar k2 = new Komentar("komentar", c, k3);
		korisnikRepository.save(k3);
		kategorijaRepository.save(k);
		clanakRepository.save(c);
		autorRepository.save(a);
		komentarRepository.save(k2);
		System.out.println("here");
		
		
	}

}
