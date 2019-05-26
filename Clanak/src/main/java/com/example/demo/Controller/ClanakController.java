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
import org.springframework.web.client.RestTemplate;

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
	@Autowired
	private DiscoveryClient discoveryClient;
	
	
	public String getKorisnikFromKorisnik(Long id) {
		List<ServiceInstance> instances=discoveryClient.getInstances("Korisnik-service");
		
		if(instances.isEmpty()) return "Servis nedostupan";
		ServiceInstance serviceInstance=instances.get(0);
		
		String baseUrl=serviceInstance.getUri().toString()+ "/korisnik/"+id.toString();
		System.out.println(baseUrl);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response=null;
		try{
		response=restTemplate.exchange(baseUrl,
				HttpMethod.GET, getHeaders(),String.class);
		}catch (Exception ex)
		{	///ovdje if(contains null) return NEMA
			return ex.getMessage();
			//return ex.getCause().toString();
			//System.out.println(ex);
		}
		//System.out.println(response.getBody());
		return response.getBody();
		
	}
	
	@GetMapping(value = "")
	public List<Clanak> getAll(){
		return clanakRepository.findAll();
	}
	
	@GetMapping(value = "/{id}")
	public Clanak getArticleById(@PathVariable(value = "id") Long id) throws NotFoundException {
		return clanakRepository.findById(id).orElseThrow(() -> new NotFoundException("Article with given id not found"));
	}
	
	@PostMapping(value = "auth")
	    @PreAuthorize("hasRole('ROLE_ADMIN')")
	    public String addGreeting(@RequestBody String greeting) {
	         // Business logic to save the greeting typically to a DB table
	         return "Greeting successfully saved";
	    }
	
	@GetMapping(value = "/link/{naziv}")
	public String getLinkByName(@PathVariable(value = "naziv") String name ) throws NotFoundException {
		List<ServiceInstance> instances=discoveryClient.getInstances("HistorijaClanak-service");
		if(instances.isEmpty()) return "greskaServis";
		ServiceInstance serviceInstance=instances.get(0);
		String baseUrl=serviceInstance.getUri().toString()+ "/clanak/link/" +name;
		System.out.println(baseUrl);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response=null;
		try{
			response  = restTemplate.exchange(baseUrl, HttpMethod.GET, getHeaders(), String.class);
			
		}catch (Exception ex)
		{	
			System.out.println(ex);
		}
		System.out.println(response.getBody());
		return response.getBody();
	}
	
	@PostMapping(value = "")
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
	@PutMapping("/{id}")
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
	 private static HttpEntity<?> getHeaders() throws IOException {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			return new HttpEntity<>(headers);
		}
}
