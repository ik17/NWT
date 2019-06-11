package com.example.demo.Controller;

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

import com.example.demo.Entities.Clanak;
import com.example.demo.Entities.Komentar;
import com.example.demo.Entities.Korisnik;
import com.example.demo.Repositories.ClanakRepository;
import com.example.demo.Repositories.KomentarRepository;
import com.example.demo.Repositories.KorisnikRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/komentar")
public class KomentarController {
	@Autowired
	KomentarRepository komentarRepository;
	@Autowired
	ClanakRepository clanakRepository;
	@Autowired
	KorisnikRepository korisnikRepository;
	@CrossOrigin
	@GetMapping(value = "")
	public List<Komentar> getAll(@RequestHeader(value="role") String acceptHeader){
		if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
			return komentarRepository.findAll();
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
		
	}
	//get by id clanka
		@CrossOrigin
		 @GetMapping("/clanak/{id}")
		    public List<Komentar> getKomentarByClanak(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
				if (acceptHeader.equals("ROLE_AUTOR")) {
					 //ovdje pocetak
					 return komentarRepository.findKomentarByIdClanak(id); //.orElseThrow(() -> new NotFoundException("Nema clanaka sa nazivom "));
					 //ovdje kraj
				 }
				else {	
						throw new AccessDeniedException("nepravilna rola");
				}
			 
			 }
	@CrossOrigin
	@GetMapping(value = "/{id}")
	public Komentar getCommentById(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
		
		if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
			return komentarRepository.findById(id).orElseThrow(() -> new NotFoundException("Comment with given id not found!"));
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
		}
	@CrossOrigin
	@PostMapping(value = "")
	public Komentar createComment(@RequestBody @Valid final Komentar komentar,@RequestHeader(value="role") String acceptHeader, Errors errors)throws Exception {
		
		if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
			if(errors.hasErrors()) {
				throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
			}
			
			Clanak clanak = clanakRepository
					.findById(komentar.getClanak().getId())
					.orElseThrow(() -> new NotFoundException("Article with given id not found!"));
			
			Korisnik korisnik = korisnikRepository
					.findById(komentar.getKorisnik().getId())
					.orElseThrow(() -> new NotFoundException("User with given id not found!"));
			
			return komentarRepository.save(komentar);
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
		
		
	}
	@CrossOrigin
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteComment(@PathVariable(value = "id") Long id,@RequestHeader(value="role") String acceptHeader) throws NotFoundException{
		if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
			Komentar komentar = komentarRepository
					.findById(id)
					.orElseThrow(() -> new NotFoundException("Comment with given id not found!"));
			komentarRepository.delete(komentar);
			return ResponseEntity.ok().build();
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
		
	}
	@CrossOrigin
	@PutMapping("/{id}")
	public Komentar updateComment(@PathVariable(value = "id") Long id, @RequestBody @Valid Komentar komentarUpdate,@RequestHeader(value="role") String acceptHeader, Errors errors)throws NotFoundException, Exception {
		if (acceptHeader.equals("ROLE_AUTOR")) {
			 //ovdje pocetak
			if(errors.hasErrors()) 
				throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
			Komentar komentar = komentarRepository
					.findById(id)
					.orElseThrow(() -> new NotFoundException("Comment with given id not found!"));
			
			Clanak clanak = clanakRepository
					.findById(komentarUpdate.getClanak().getId())
					.orElseThrow(() -> new NotFoundException("Article with given id not found!"));
			Korisnik korisnik = korisnikRepository
					.findById(komentarUpdate.getKorisnik().getId())
					.orElseThrow(() -> new NotFoundException("User with given id not found!"));
			
			komentar.setClanak(komentarUpdate.getClanak());
			komentar.setKorisnik(komentarUpdate.getKorisnik());
			komentar.setTextKomentara(komentarUpdate.getTextKomentara());
			
			komentarUpdate = komentarRepository.save(komentar);
			return komentarUpdate;
			 //ovdje kraj
		 }
		else {		
				throw new AccessDeniedException("nepravilna rola");
			}
		
		
		
	}
	
}
