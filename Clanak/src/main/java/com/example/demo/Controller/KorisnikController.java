package com.example.demo.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.example.demo.Entities.Korisnik;
import com.example.demo.Repositories.KorisnikRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/korisnik")
public class KorisnikController {
	@Autowired
	KorisnikRepository korisnikRepository;
	
	@CrossOrigin
	@GetMapping(value = "")
	public List<Korisnik> getAll(@RequestHeader(value="role") String acceptHeader){
		
		if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
			return korisnikRepository.findAll();
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
		
	}
	@CrossOrigin
	@GetMapping(value = "/{id}")
	public Korisnik getUserById(@PathVariable(value = "id") Long id,@RequestHeader(value="role") String acceptHeader) throws NotFoundException {
		
		if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
			return korisnikRepository.findById(id).orElseThrow(() -> new NotFoundException("User with given id not found!"));
			
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
		}
	@CrossOrigin
	@PostMapping(value = "")
	public Korisnik createUser(@RequestBody @Valid final Korisnik korisnik,@RequestHeader(value="role") String acceptHeader, Errors errors)throws Exception {
		/*if(errors.hasErrors()) {
			throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		}*/
		
		if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
			return korisnikRepository.save(korisnik);
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
	}
	@CrossOrigin
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id,@RequestHeader(value="role") String acceptHeader) throws NotFoundException{
		if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
			Korisnik korisnik = korisnikRepository
					.findById(id)
					.orElseThrow(() -> new NotFoundException("User with given id not found!"));
			korisnikRepository.delete(korisnik);
			return ResponseEntity.ok().build();
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
		
	}
	@CrossOrigin
	@PutMapping("/{id}")
	public Korisnik updateKorisnik(@PathVariable(value = "id") Long id, @RequestBody @Valid Korisnik korisnikUpdate,@RequestHeader(value="role") String acceptHeader, Errors errors) throws NotFoundException, Exception{
		if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
			/*if(errors.hasErrors()) 
			throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());*/
		Korisnik korisnik = korisnikRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("User with given id not found!"));
		korisnik.setUsername(korisnikUpdate.getUsername());
		korisnikUpdate = korisnikRepository.save(korisnik);
		return korisnikUpdate;
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
		
		
	}  
	
}
