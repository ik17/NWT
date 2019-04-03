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

import com.example.demo.Entities.Clanak;
import com.example.demo.Entities.Kategorija;
import com.example.demo.Entities.Korisnik;
import com.example.demo.Repositories.ClanakRepository;
import com.example.demo.Repositories.KategorijaRepository;
import com.example.demo.Repositories.KorisnikRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/clanak")
public class ClanakController {
	@Autowired
	ClanakRepository clanakRepository;
	@Autowired
	KorisnikRepository korisnikRepository;
	@Autowired
	KategorijaRepository kategorijaRepository;
	
	@GetMapping(value = "/all")
	public List<Clanak> getAll(){
		return clanakRepository.findAll();
	}
	
	@GetMapping(value = "/get/{id}")
	public Clanak getArticleById(@PathVariable(value = "id") Long id) throws NotFoundException {
		return clanakRepository.findById(id).orElseThrow(() -> new NotFoundException("Article with given id not found"));
	}
	
	@PostMapping(value = "/insert")
	public Clanak createArticle(@RequestBody @Valid final Clanak clanak, Errors errors) throws Exception {
		if(errors.hasErrors()) {
			throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		}
		Korisnik k = korisnikRepository
				.findById(clanak.getOdobrioClanak().getId())
				.orElseThrow(() -> new NotFoundException("User with given id not found!"));
		Kategorija k2 = kategorijaRepository
				.findById(clanak.getKategorija().getId())
				.orElseThrow(() -> new NotFoundException("Category with given id not found!"));
		return clanakRepository.save(clanak);
	}
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deleteArticle(@PathVariable(value = "id") Long id) throws NotFoundException{
		Clanak clanak = clanakRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("Article with given id not found!"));
		clanakRepository.delete(clanak);
		return ResponseEntity.ok().build();
	}
	@PutMapping("/update/{id}")
	public Clanak updateArticle(@PathVariable(value = "id") Long id, @RequestBody @Valid Clanak clanakUpdate, Errors errors) throws NotFoundException, Exception{
		if(errors.hasErrors()) 
			throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		Clanak clanak = clanakRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("Article with given id not found!"));
		Korisnik k = korisnikRepository
				.findById(clanakUpdate.getOdobrioClanak().getId())
				.orElseThrow(() -> new NotFoundException("User with given id not found!"));
		Kategorija k2 = kategorijaRepository
				.findById(clanakUpdate.getKategorija().getId())
				.orElseThrow(() -> new NotFoundException("Category with given id not found!"));
		
		clanak.setClanakOdobren(clanakUpdate.getClanakOdobren());
		clanak.setKategorija(clanakUpdate.getKategorija());
		clanak.setNaziv(clanakUpdate.getNaziv());
		clanak.setOdobrioClanak(clanakUpdate.getOdobrioClanak());
		
		clanakUpdate = clanakRepository.save(clanak);
		return clanakUpdate;
	} 
}
