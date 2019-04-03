package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Clanak;
import com.example.demo.entity.Kategorija;
import com.example.demo.entity.Korisnik;
import com.example.demo.repository.ClanakRepository;
import com.example.demo.repository.KategorijaRepository;
import com.example.demo.repository.KorisnikRepository;

import ch.qos.logback.core.net.server.Client;
import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/clanak")
public class ClanakController {
	@Autowired
	ClanakRepository cR;
	
	@Autowired
	KategorijaRepository kR;
	
	@Autowired
	KorisnikRepository kkR;
	
	@GetMapping(value="/all")
    public List<Clanak> getAll(){
        return cR.findAll();
    }
	
	 @GetMapping("/get/{id}")
	    public Clanak getClientById(@PathVariable(value = "id") Long id) throws NotFoundException {
	        return cR.findById(id).orElseThrow(() -> new NotFoundException("Article with given id not found"));
	    }
	 
	 @PostMapping(value="/insert")
	    public Clanak createClanak(@RequestBody @Valid final Clanak clanak, Errors errors) throws Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }
	        Kategorija kategorija = kR
	                .findById(clanak.getIdKategorije().getId())
	                .orElseThrow(
	                        () -> new NotFoundException("Category with given id not found")
	                );
	        
	        Korisnik korisnik= kkR
	                .findById(clanak.getOdobrioClanak().getId())
	                .orElseThrow(
	                        () -> new NotFoundException("User with given id not found")
	                );

	        return cR.save(clanak);
	    }
	 
	 @PutMapping("update/{id}")
	    public Clanak updateClanak(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid Clanak clanakUpdated, Errors errors) throws NotFoundException, Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }

	        Clanak clanak = cR
	                .findById(id)
	                .orElseThrow(
	                        () -> new NotFoundException("Article with given id not found")
	                );
	        
	        Kategorija kategorija = kR
	                .findById(clanakUpdated.getIdKategorije().getId())
	                .orElseThrow(
	                        () -> new NotFoundException("Category with given id not found")
	                );
	        
	        Korisnik korisnik= kkR
	                .findById(clanakUpdated.getOdobrioClanak().getId())
	                .orElseThrow(
	                        () -> new NotFoundException("User with given id not found")
	                );
	        
	        clanak.setClanakOobren(clanakUpdated.getClanakOdobren());
	        clanak.setIdKategorije(kategorija);
	        clanak.setNaziv(clanakUpdated.getNaziv());
	        clanak.setOdobrioClanak(korisnik);
	       

	        clanakUpdated = cR.save(clanak);
	        return clanakUpdated;
	    }
	 @DeleteMapping("delete/{id}")
	    public ResponseEntity<?> deleteClanak(@PathVariable(value = "id") Long id) throws NotFoundException {
	        Clanak clanak = cR.findById(id)
	                .orElseThrow(() -> new NotFoundException("Clanak with given id not found"));

	        cR.delete(clanak);

	        return ResponseEntity.ok().build();
	    }

}