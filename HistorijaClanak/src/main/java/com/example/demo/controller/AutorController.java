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

import com.example.demo.entity.Autor;
import com.example.demo.entity.Clanak;
import com.example.demo.entity.Korisnik;
import com.example.demo.repository.AutorRepository;
import com.example.demo.repository.ClanakRepository;
import com.example.demo.repository.KorisnikRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/autor")
public class AutorController {
	@Autowired
	AutorRepository aR;

	@Autowired
	KorisnikRepository kkR;
	
	@Autowired
	ClanakRepository cR;
	@CrossOrigin
	@GetMapping(value="")
    public List<Autor> getAll(@RequestHeader(value="role") String acceptHeader){
		if (acceptHeader.equals("ROLE_REVIEWER")) {
        return aR.findAll();
		}
		else {
			
			throw new AccessDeniedException("nepravilna rola");
		}
    }
	@CrossOrigin
	 @GetMapping("/{id}")
	    public Autor getAutorById(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 return aR.findById(id).orElseThrow(() -> new NotFoundException("Autor with given id not found"));
			   	}
		else {
					
				throw new AccessDeniedException("nepravilna rola");
			}
		  }
	
	//get by id clanka
	@CrossOrigin
	 @GetMapping("/clanak/{id}")
	    public List<Autor> getAutorByClanak(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
			if (acceptHeader.equals("ROLE_REVIEWER")) {
				 //ovdje pocetak
				 return aR.findAutorByIdClanak(id); //.orElseThrow(() -> new NotFoundException("Nema clanaka sa nazivom "));
				 //ovdje kraj
			 }
			else {	
					throw new AccessDeniedException("nepravilna rola");
			}
		 
		 }
	
	@CrossOrigin
	 @PostMapping(value="")
	    public Autor createAutor(@RequestBody @Valid final Autor autor, @RequestHeader(value="role") String acceptHeader,Errors errors) throws Exception {

		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak

			 
		        if(errors.hasErrors()){
		            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		        }
		        Korisnik korisnik= kkR
		                .findById(autor.getIdKorisnik().getId())
		                .orElseThrow(
		                        () -> new NotFoundException("User with given id not found")
		                );
		        Clanak clanak = cR
		                .findById(autor.getIdClanak().getId())
		                .orElseThrow(
		                        () -> new NotFoundException("Article with given id not found")
		                );
		       

		        return aR.save(autor);
			 //ovdje kraj
		 }
		else {
					
				throw new AccessDeniedException("nepravilna rola");
			}
		 
		 
		 
	    }
	@CrossOrigin
	 @PutMapping("/{id}")
	    public Autor updateAutor(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid Autor autorUpdated,@RequestHeader(value="role") String acceptHeader, Errors errors) throws NotFoundException, Exception {

		 
		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			 if(errors.hasErrors()){
		            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		        }

		        Autor autor = aR
		                .findById(id)
		                .orElseThrow(
		                        () -> new NotFoundException("Autor with given id not found")
		                );
		        Korisnik korisnik= kkR
		                .findById(autorUpdated.getIdKorisnik().getId())
		                .orElseThrow(
		                        () -> new NotFoundException("User with given id not found")
		                );
		        Clanak clanak = cR
		                .findById(autorUpdated.getIdClanak().getId())
		                .orElseThrow(
		                        () -> new NotFoundException("Article with given id not found")
		                );
		       
		        autor.setIdClanak(clanak);   
		        autor.setIdKorisnik(korisnik);
		        autorUpdated = aR.save(autor);
		        return autorUpdated;

			 
			 //ovdje kraj
		 }
		else {
					
				throw new AccessDeniedException("nepravilna rola");
		}
		 
		 
	        
	    }
	@CrossOrigin
	 @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteAutor(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
	        Autor autor = aR.findById(id)
	                .orElseThrow(() -> new NotFoundException("Autor with given id not found"));

	   	 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			
	   	  aR.delete(autor);

	        return ResponseEntity.ok().build();
			 //ovdje kraj
		 }
		else {
					
				throw new AccessDeniedException("nepravilna rola");
		}
	        
	        
	        
	        
	      
	    }
}
