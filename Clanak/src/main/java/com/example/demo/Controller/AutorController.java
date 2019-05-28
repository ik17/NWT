package com.example.demo.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Entities.Autor;
import com.example.demo.Entities.Clanak;
import com.example.demo.Entities.Korisnik;
import com.example.demo.Repositories.AutorRepository;
import com.example.demo.Repositories.ClanakRepository;
import com.example.demo.Repositories.KorisnikRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/autor")
public class AutorController {
	@Autowired
	AutorRepository autorRepository;
	@Autowired
	ClanakRepository clanakRepository;
	@Autowired
	KorisnikRepository korisnikRepository;
	
	//@PreAuthorize("hasRole('Autor')")
	@GetMapping(value = "")
	public List<Autor> getAll(){
		return autorRepository.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public Autor getAuthorById(@PathVariable(value = "id") Long id) throws NotFoundException {
		return autorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author with given id not found"));
	}
	
	@PostMapping(value = "")
	public Autor createAuthor(@RequestBody @Valid final Autor autor, Errors errors) throws Exception {
		if(errors.hasErrors()) {
			throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		}
		Clanak c = clanakRepository
				.findById(autor.getClanak().getId())
				.orElseThrow(() -> new NotFoundException("Article with given id not found"));
		Korisnik k = korisnikRepository
				.findById(autor.getKorisnik().getId())
				.orElseThrow(() -> new NotFoundException("User with given id not found"));
		return autorRepository.save(autor);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVersion(@PathVariable(value = "id") Long id) throws NotFoundException{
		Autor autor = autorRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("Author with given id not found"));
		
		autorRepository.delete(autor);
		
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{id}")
	public Autor updateAuthor(@PathVariable(value = "id") Long id, @RequestBody @Valid Autor autorUpdate, Errors errors) throws NotFoundException, Exception {
		if(errors.hasErrors()) 
			throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		
		Autor autor = autorRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("Author with given id not found"));
		Clanak c = clanakRepository
				.findById(autorUpdate.getClanak().getId())
				.orElseThrow(() -> new NotFoundException("Article with given id not found"));
		Korisnik k = korisnikRepository
				.findById(autorUpdate.getKorisnik().getId())
				.orElseThrow(() -> new NotFoundException("User with given id not found"));
		
		autor.setClanak(autorUpdate.getClanak());
		autor.setKorisnik(autorUpdate.getKorisnik());
		
		autorUpdate = autorRepository.save(autor);
		return autorUpdate;
	}
	
}
