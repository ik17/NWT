package com.example.demo.Controller;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
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
import org.springframework.web.client.RestTemplate;

import com.example.demo.Entities.Kategorija;
import com.example.demo.Repositories.KategorijaRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/kategorija")
public class KategorijaController {
	@Autowired
	KategorijaRepository kategorijaRepository;
	@Autowired
	private DiscoveryClient discoveryClient;
	@GetMapping(value = "")
	public List<Kategorija> getAll(){
		return kategorijaRepository.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public Kategorija getCategoryById(@PathVariable(value = "id") Long id) throws NotFoundException {
		return kategorijaRepository.findById(id).orElseThrow(() -> new NotFoundException("Category with given id not found!"));
	}
	
	@PostMapping(value = "")
	public Kategorija createCategory(@RequestBody @Valid final Kategorija kategorija, Errors errors) throws Exception {
		if(errors.hasErrors()) {
			throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		}
		
		
			
		return kategorijaRepository.save(kategorija);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id)throws NotFoundException{
		Kategorija kategorija = kategorijaRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("Category with given id not found!"));
		
		kategorijaRepository.delete(kategorija);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{id}")
	public Kategorija updateCategory(@PathVariable(value = "id") Long id, @RequestBody @Valid Kategorija kategorijaUpdate, Errors errors) throws NotFoundException, Exception{
		if(errors.hasErrors()) 
			throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		Kategorija kategorija = kategorijaRepository
				.findById(id)
				.orElseThrow(() -> new NotFoundException("Category with given id not found!"));
		kategorija.setNaziv(kategorijaUpdate.getNaziv());
		
		kategorijaUpdate = kategorijaRepository.save(kategorija);
		return kategorijaUpdate;
	}
	
	 private static HttpEntity<?> getHeaders() throws IOException {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			return new HttpEntity<>(headers);
		}
	
}
