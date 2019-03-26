package com.example.demo;





import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.example.demo.entity.Korisnik;
import com.example.demo.repository.KorisnikRepository;


@EnableDiscoveryClient
@SpringBootApplication
public class HistorijaClanakApplication implements CommandLineRunner {
	 

@Autowired
KorisnikRepository korisnikRepository;

	public static void main(String[] args)  {
		SpringApplication.run(HistorijaClanakApplication.class, args);
		
	}
	@Override

	    public void run(String... arg0) throws Exception {
	
	System.out.println("Helllooo");
	//Korisnik korisnik = korisnikRepository.findById(5);
	//Connection c = dataSource.getConnection();
	//System.out.println(korisnik.getUsername());

	korisnikRepository.save(new Korisnik("Evee"));
	 }
}
