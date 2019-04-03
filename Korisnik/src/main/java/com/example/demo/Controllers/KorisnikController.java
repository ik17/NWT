package com.example.demo.Controllers;

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

import com.example.demo.Entities.Korisnik;
import com.example.demo.Repositories.KorisnikRepository;


import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/korisnik")
public class KorisnikController {
	
	@Autowired
	KorisnikRepository korisnikRepo;
	
	@GetMapping(value="/all")
    public List<Korisnik> getAll(){
        return korisnikRepo.findAll();
    }
	
	 @GetMapping("/get/{id}")
	    public Korisnik getKorisnikById(@PathVariable(value = "id") Long id) throws NotFoundException {
	        return korisnikRepo.findById(id).orElseThrow(() -> new NotFoundException("User with given id not found"));
	    }
	 
	 @PostMapping(value="/insert")
	    public Korisnik createKorisnik(@RequestBody @Valid final Korisnik korisnik, Errors errors) throws Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }

	        return korisnikRepo.save(korisnik);
	    }
	 
	 @PutMapping("update/{id}")
	    public Korisnik updateKorisnik(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid Korisnik korisnikUpdate, Errors errors) throws NotFoundException, Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }

	        Korisnik korisnik = korisnikRepo
	                .findById(id)
	                .orElseThrow(
	                        () -> new NotFoundException("User with given id not found")
	                );
	        
	        korisnik.setUsername(korisnikUpdate.getUsername());
	        korisnik.setPassword(korisnikUpdate.getPassword());
	        korisnik.setKorisnikPodaci(korisnikUpdate.getKorisnikPodaci());
	        korisnik.setUlogaKorisnik(korisnikUpdate.getUlogaKorisnik());
	        
	        korisnikUpdate = korisnikRepo.save(korisnik);
	        return korisnikUpdate;
	    }
	 
	 @DeleteMapping("delete/{id}")
	    public ResponseEntity<?> deleteKorisnik(@PathVariable(value = "id") Long id) throws NotFoundException {
	        Korisnik korisnik = korisnikRepo.findById(id)
	                .orElseThrow(() -> new NotFoundException("User with given id not found"));

	        korisnikRepo.delete(korisnik);

	        return ResponseEntity.ok().build();
	    }


}
