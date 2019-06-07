package com.example.demo.Controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
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
	
	@CrossOrigin
	@GetMapping(value = "")
	public List<Autor> getAll(@RequestHeader(value="role") String acceptHeader){
		 if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
			 return autorRepository.findAll();
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
	}
	@CrossOrigin
	@GetMapping(value = "/{id}")
	public Autor getAuthorById(@PathVariable(value = "id") Long id,@RequestHeader(value="role") String acceptHeader) throws NotFoundException {
		 if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
			 return autorRepository.findById(id).orElseThrow(() -> new NotFoundException("Author with given id not found"));

			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
		}
	@CrossOrigin
	@PostMapping(value = "")
	public Autor createAuthor(@RequestBody @Valid final Autor autor,@RequestHeader(value="role") String acceptHeader, Errors errors) throws Exception {
		
		 if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
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
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
		
		
		
	}
	@CrossOrigin
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteVersion(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException{
		 if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
				
				Autor autor = autorRepository
						.findById(id)
						.orElseThrow(() -> new NotFoundException("Author with given id not found"));
				
				autorRepository.delete(autor);
				
				return ResponseEntity.ok().build();
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
	
	}
	@CrossOrigin
	@PutMapping("/{id}")
	public Autor updateAuthor(@PathVariable(value = "id") Long id, @RequestBody @Valid Autor autorUpdate,@RequestHeader(value="role") String acceptHeader, Errors errors) throws NotFoundException, Exception {
		
		 if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
				
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
				
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
	
	}
	
}
