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

import com.example.demo.entity.Korisnik;
import com.example.demo.repository.KorisnikRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/korisnik")
public class KorisnikController {
	
	@Autowired
	KorisnikRepository kR;
	
	@GetMapping(value="")
    public List<Korisnik> getAll(){
        return kR.findAll();
    }
	
	 @GetMapping("/{id}")
	    public Korisnik getCategoryById(@PathVariable(value = "id") Long id) throws NotFoundException {
	        return kR.findById(id).orElseThrow(() -> new NotFoundException("User with given id not found"));
	    }
	 
	 @PostMapping(value="")
	    public Korisnik createUder(@RequestBody @Valid final Korisnik korisnik, Errors errors) throws Exception {

	        //if(errors.hasErrors()){
	          //  throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        //}
		 	System.out.println("In");
	        return kR.save(korisnik);
	    }
	 
	 @PutMapping("/{id}")
	    public Korisnik updateUser(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid Korisnik korisnikUpdated, Errors errors) throws NotFoundException, Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }

	        Korisnik korisnik = kR
	                .findById(id)
	                .orElseThrow(
	                        () -> new NotFoundException("User with given id not found")
	                );
	        korisnik.setUsername(korisnikUpdated.getUsername());
	        
	       

	        korisnikUpdated = kR.save(korisnik);
	        return korisnikUpdated;
	    }
	 @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id) throws NotFoundException {
	        Korisnik korisnik = kR.findById(id)
	                .orElseThrow(() -> new NotFoundException("User with given id not found"));

	        kR.delete(korisnik);

	        return ResponseEntity.ok().build();
	    }


}
