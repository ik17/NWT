package com.example.demo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
	@CrossOrigin
	@GetMapping(value="")
    public List<Korisnik> getAll(@RequestHeader(value="role") String acceptHeader){
		System.out.println("in korisnik");
		if (acceptHeader.equals("ROLE_REVIEWER")) {
			System.out.println("in korisnik2");
			 //ovdje pocetak
			return kR.findAll();
			 //ovdje kraj
		 }
		else {	
			System.out.println("in korisnik3");
				throw new AccessDeniedException("nepravilna rola");
		}
        
    }
	@CrossOrigin
	 @GetMapping("/{id}")
	    public Korisnik getCategoryById(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			 return kR.findById(id).orElseThrow(() -> new NotFoundException("User with given id not found"));
			   
			 //ovdje kraj
		 }
		else {	
				throw new AccessDeniedException("nepravilna rola");
		}
		 
		 }
	/*@CrossOrigin
	 @PostMapping(value="")
	    public Korisnik createUder(@RequestBody @Valid final Korisnik korisnik,@RequestHeader(value="role") String acceptHeader, Errors errors) throws Exception {

		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			 System.out.println("In");
		        return kR.save(korisnik);
			 //ovdje kraj
		 }
		else {	
				throw new AccessDeniedException("nepravilna rola");
		}
		 
	        //if(errors.hasErrors()){
	          //  throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        //}
		 	
	    }*/
	@CrossOrigin
	 @PostMapping(value="")
	    public Korisnik createUder(@RequestBody @Valid final Korisnik korisnik, Errors errors) throws Exception {
			 System.out.println("In");
		     return kR.save(korisnik);
	    }
	@CrossOrigin
	 @PutMapping("/{id}")
	    public Korisnik updateUser(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid Korisnik korisnikUpdated,@RequestHeader(value="role") String acceptHeader, Errors errors) throws NotFoundException, Exception {

		 
		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			// if(errors.hasErrors()){
		       //     throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		       // }

		        Korisnik korisnik = kR
		                .findById(id)
		                .orElseThrow(
		                        () -> new NotFoundException("User with given id not found")
		                );
		        korisnik.setUsername(korisnikUpdated.getUsername());
		        
		       

		        korisnikUpdated = kR.save(korisnik);
		        return korisnikUpdated;
			 //ovdje kraj
		 }
		else {	
				throw new AccessDeniedException("nepravilna rola");
		}
	       
	    }
	@CrossOrigin
	 @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
	        
		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			 Korisnik korisnik = kR.findById(id)
		                .orElseThrow(() -> new NotFoundException("User with given id not found"));

		        kR.delete(korisnik);

		        return ResponseEntity.ok().build();
			 //ovdje kraj
		 }
		else {	
				throw new AccessDeniedException("nepravilna rola");
		}
		 
		
	    }


}
