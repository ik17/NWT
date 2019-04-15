package com.example.demo;



import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.demo.controller.ClanakController;
import com.example.demo.entity.AVerzija;
import com.example.demo.entity.Autor;
import com.example.demo.entity.Clanak;
import com.example.demo.entity.Kategorija;
import com.example.demo.entity.Korisnik;
import com.example.demo.repository.AVerzijaRepository;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.ClanakRepository;
import com.example.demo.repository.KategorijaRepository;
import com.example.demo.repository.KorisnikRepository;

import javassist.NotFoundException;

@RunWith(SpringRunner.class)
@SpringBootTest
//@DataJpaTest
public class HistorijaClanakApplicationTests {
	//@Mock
	@Autowired
	KorisnikRepository korisnikRepository;
	//@Mock
	@Autowired
	KategorijaRepository kR;
	//@Mock
	@Autowired
	AVerzijaRepository vR;
	//@Mock
	@Autowired
	AutorRepository aR;
	//@Mock
	@Autowired
	ClanakRepository cR;
	//@InjectMocks
	@Autowired
	ClanakController ccR;
	
	//@Before
    //public void init() {
     //   MockitoAnnotations.initMocks(this);
    //}


	@Test
	public void contextLoads() {
	}
	
	@Test
	public void whenFindByName_thenReturnEmployee() throws NotFoundException {
	  
	   
	    
	    //given
	    Kategorija kk1 = new Kategorija("kategorija2");
    	Kategorija kk2 = new Kategorija("Kategorija3");
    	kR.save(kk1);
    	kR.save(kk2);
    	Korisnik k1 = new Korisnik("Billy");
    	Korisnik k2 = new Korisnik("Samantha");
    	korisnikRepository.save(k1);
    	korisnikRepository.save(k2);
    	Clanak c1 = new Clanak("Clanak1", kk1, 0, k1);
    	Clanak c2 = new Clanak("Clanak2", kk1, 0, k1);
    	cR.save(c1);
    	cR.save(c2);
    	AVerzija v1 = new AVerzija(2, "linkNaClanak1", "review ovaj članak je dobar i jako mi se svidja, odobreno",c1);
    	AVerzija v2 = new AVerzija(1, "linkNaClanak11", "review ovaj članak je dobar i jako mi se svidja, odobreno",c2);
    	AVerzija v3 = new AVerzija(3, "linkNaClanak2", "review ovaj članak je dobar i jako mi se svidja, odobreno",c1);
    	vR.save(v1);
    	vR.save(v2);
    	vR.save(v3);
    	
    	//when
    	String linkFound = ccR.getLink2("Clanak1");
    	
    	
    	//Optional<Clanak> c = cR.findById(new Long(1));
    	//System.out.println(c.get().getNaziv());
    	//System.out.println(c.get().getIdKategorije().getNaziv());
    	//System.out.println(ccR.getLink2("Clanak1"));
	    
	 
	    // then
	    assertThat(linkFound)
	      .isEqualTo(v3.getLink());
	}

}
