package com.example.demo;





import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.jdbc.datasource.DataSourceUtils;

import com.example.demo.entity.Korisnik;
import com.example.demo.repository.KorisnikRepository;
import com.example.demo.entity.Kategorija;
import com.example.demo.repository.KategorijaRepository;
import com.example.demo.entity.Clanak;
import com.example.demo.repository.ClanakRepository;
import com.example.demo.entity.AVerzija;
import com.example.demo.repository.AVerzijaRepository;
import com.example.demo.entity.Autor;
import com.example.demo.repository.AutorRepository;
@EnableDiscoveryClient
@SpringBootApplication
@EnableAutoConfiguration
public class HistorijaClanakApplication implements CommandLineRunner {
	 

@Autowired
KorisnikRepository korisnikRepository;
@Autowired
KategorijaRepository kR;
@Autowired
AVerzijaRepository vR;
@Autowired
AutorRepository aR;
@Autowired
ClanakRepository cR;


	public static void main(String[] args)  {
		SpringApplication.run(HistorijaClanakApplication.class, args);
		
	}
	@Override

	    public void run(String... arg0) throws Exception {
	
	System.out.println("Helllooo");
	//Korisnik korisnik = korisnikRepository.findById(5);
	//Connection c = dataSource.getConnection();
	//System.out.println(korisnik.getUsername());
	Kategorija kk1 = new Kategorija("kategorija2");
	Kategorija kk2 = new Kategorija("Kategorija3");
	kR.save(kk1);
	kR.save(kk2);
	Korisnik k1 = new Korisnik("Billy");
	Korisnik k2 = new Korisnik("Samantha");
	korisnikRepository.save(k1);
	korisnikRepository.save(k2);
	Clanak c1 = new Clanak("Clanak1", kk1, 0, k1);
	Clanak c2 = new Clanak("Clanak1", kk1, 0, k1);
	cR.save(c1);
	cR.save(c2);
	AVerzija v1 = new AVerzija(2, "linkNaClanak", "review ovaj članak je dobar i jako mi se svidja, odobreno",c1);
	AVerzija v2 = new AVerzija(1, "linkNaClanak", "review ovaj članak je dobar i jako mi se svidja, odobreno",c2);
	AVerzija v3 = new AVerzija(3, "linkNaClanak", "review ovaj članak je dobar i jako mi se svidja, odobreno",c1);
	vR.save(v1);
	vR.save(v2);
	vR.save(v3);
	Autor a1 = new Autor(k1,c2);
	Autor a2 = new Autor(k2,c1);
	aR.save(a1);
	aR.save(a2);
	
	Optional<Clanak> c = cR.findById(new Long(1));
	System.out.println(c.get().getNaziv());
	System.out.println(c.get().getIdKategorije().getNaziv());
	
	
	
	 }
}
