package com.example.demo.Controller;

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
	KorisnikRepository korisnikRepository;
	
	@GetMapping(value = "")
	public List<Korisnik> getAll(){
		return korisnikRepository.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public Korisnik getUserById(@PathVariable(value = "id") Long id) throws NotFoundException {
		return korisnikRepository.findById(id).orElseThrow(() -> new NotFoundException("User with given id not found!"));
	}
	
	@PostMapping(value = "")
	public Korisnik createUser(@RequestBody @Valid final Korisnik korisnik, Errors errors)throws Exception {
		/*if(errors.hasErrors()) {
			throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		}*/
		return korisnikRepository.save(korisnik);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable(value = "id") Long id) throws NotFoundException{
		Korisnik korisnik = korisnikRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("User with given id not found!"));
		korisnikRepository.delete(korisnik);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{id}")
	public Korisnik updateKorisnik(@PathVariable(value = "id") Long id, @RequestBody @Valid Korisnik korisnikUpdate, Errors errors) throws NotFoundException, Exception{
		/*if(errors.hasErrors()) 
			throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());*/
		Korisnik korisnik = korisnikRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("User with given id not found!"));
		korisnik.setUsername(korisnikUpdate.getUsername());
		korisnikUpdate = korisnikRepository.save(korisnik);
		return korisnikUpdate;
	}  
	
}
