package com.example.demo;

import java.util.List;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.example.demo.Controller.ClanakController;
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

@Component
public class Runner  implements CommandLineRunner {
	private final RabbitTemplate rabbitTemplate;
    private final Receiver receiver;

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
    @Autowired
    ClanakController ccC;
    
    
    public Runner(Receiver receiver, RabbitTemplate rabbitTemplate) {
    	
        this.receiver = receiver;
        this.rabbitTemplate = rabbitTemplate;
        
        
        
    }
    @Override
    public void run(String... args) throws Exception {
    	Korisnik k3 = new Korisnik("Korisnik");
		Korisnik k32 = new Korisnik("Korisnik2");
		Kategorija k = new Kategorija("kategorija");
		Clanak c = new Clanak("naziv", k, true, k3);
		Autor a = new Autor(c, k3);
		Autor a2 = new Autor(c, k32);
		Clanak c2 = new Clanak("naziv2", k, true, k3);
		
		Komentar k2 = new Komentar("komentar", c, k3);
		korisnikRepository.save(k3);
		korisnikRepository.save(k32);
		kategorijaRepository.save(k);
		clanakRepository.save(c);
		clanakRepository.save(c2);
		autorRepository.save(a);
		autorRepository.save(a2);
		komentarRepository.save(k2);
		List<Clanak> listaa = clanakRepository.findClanakByNaziv("naziv");
		List<Autor> lista2 = autorRepository.findAutorByIdClanak(c.getId());
		//System.out.println(listaa.isEmpty());
		//System.out.println(listaa.get(1).getNaziv());
		//System.out.println(lista2.get(0).getKorisnik().getUsername());
		//System.out.println(lista2.get(1).getKorisnik().getUsername());
		//System.out.println(clanakRepository.findClanakByNaziv("naziv").get(1).getNaziv());
    	//System.out.println(ccC.getKorisnikFromKorisnik(1L));
		//System.out.println("here");
		
    }
}
