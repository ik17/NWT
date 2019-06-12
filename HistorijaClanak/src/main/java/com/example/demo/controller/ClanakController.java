package com.example.demo.controller;

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
import org.springframework.web.client.RestTemplate;

import com.example.demo.entity.Clanak;
import com.example.demo.entity.Kategorija;
import com.example.demo.entity.Korisnik;
import com.example.demo.repository.AVerzijaRepository;
import com.example.demo.repository.ClanakRepository;
import com.example.demo.repository.KategorijaRepository;
import com.example.demo.repository.KorisnikRepository;

import ch.qos.logback.core.net.server.Client;
import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/clanak")
public class ClanakController {
	@Autowired
	ClanakRepository cR;
	@Autowired
	AVerzijaRepository aR;
	@Autowired
	KategorijaRepository kR;
	
	@Autowired
	KorisnikRepository kkR;
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
	
	
	
	
	@CrossOrigin
	@GetMapping(value="")
    public List<Clanak> getAll(@RequestHeader(value="role") String acceptHeader){
		
		if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			return cR.findAll();
			 //ovdje kraj
		 }
		else {	
				throw new AccessDeniedException("nepravilna rola");
		}
		
        
    }
	@CrossOrigin
	 @GetMapping("/{id}")
	    public Clanak getClientById(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
			if (acceptHeader.equals("ROLE_REVIEWER")) {
				 //ovdje pocetak
				 return cR.findById(id).orElseThrow(() -> new NotFoundException("Article with given id not found"));
				 //ovdje kraj
			 }
			else {	
					throw new AccessDeniedException("nepravilna rola");
			}
		 
		 }
	@CrossOrigin
	 @GetMapping(value="/linkId/{id}")
	    public String getLink(@PathVariable(value = "id") Long id) throws NotFoundException{
	        //Clanak c =  cR.findById(id).orElseThrow(() -> new NotFoundException("Article with given id not found"));
	        return aR.findLink(id);
	 }
	@CrossOrigin
	 @GetMapping(value="/link/{name}")
	    public String getLink2(@PathVariable(value = "name") String name) throws NotFoundException{
	        //Clanak c =  cR.findById(id).orElseThrow(() -> new NotFoundException("Article with given id not found"));
	       // return aR.findLink(id);
		 Long id = cR.getIdByName(name); 
		 return aR.findLink(id);
				 
				// cR.findById(id).orElseThrow(() -> new NotFoundException("Article with given id not found"));
	 }
	@CrossOrigin
	 @PostMapping(value="")
	    public Clanak createClanak(@RequestBody @Valid final Clanak clanak,@RequestHeader(value="role") String acceptHeader, Errors errors) throws Exception {

		 if (acceptHeader.equals("ROLE_REVIEWER") || acceptHeader.contentEquals("ROLE_AUTOR")) {
			 //ovdje pocetak

		        if(errors.hasErrors()){
		            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		        }
		        Kategorija kategorija = kR
		                .findById(clanak.getIdKategorije().getId())
		                .orElseThrow(
		                        () -> new NotFoundException("Category with given id not found")
		                );
		        /*
		        Korisnik korisnik= kkR
		                .findById(clanak.getOdobrioClanak().getId())
		                .orElseThrow(
		                        () -> new NotFoundException("User with given id not found")
		                );*/

		        return cR.save(clanak);
		        //ovdje kraj
		 }
		else {	
				throw new AccessDeniedException("nepravilna rola");
		}
		 
	    }
	@CrossOrigin
	 @PutMapping("/{id}")
	    public Clanak updateClanak(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid Clanak clanakUpdated,@RequestHeader(value="role") String acceptHeader, Errors errors) throws NotFoundException, Exception {

		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			  if(errors.hasErrors()){
		            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		        }

		        Clanak clanak = cR
		                .findById(id)
		                .orElseThrow(
		                        () -> new NotFoundException("Article with given id not found")
		                );
		        
		        Kategorija kategorija = kR
		                .findById(clanakUpdated.getIdKategorije().getId())
		                .orElseThrow(
		                        () -> new NotFoundException("Category with given id not found")
		                );
		        
		        Korisnik korisnik= kkR
		                .findById(clanakUpdated.getOdobrioClanak().getId())
		                .orElseThrow(
		                        () -> new NotFoundException("User with given id not found")
		                );
		        
		        clanak.setClanakOobren(clanakUpdated.getClanakOdobren());
		        clanak.setIdKategorije(kategorija);
		        clanak.setNaziv(clanakUpdated.getNaziv());
		        clanak.setOdobrioClanak(korisnik);
		       

		        clanakUpdated = cR.save(clanak);
		        return clanakUpdated;
		        
		     //ovdje kraj
		 }
		else {	
				throw new AccessDeniedException("nepravilna rola");
		}
 
	      
	    }
	@CrossOrigin
	 @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteClanak(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
	        Clanak clanak = cR.findById(id)
	                .orElseThrow(() -> new NotFoundException("Clanak with given id not found"));

	        
	        if (acceptHeader.equals("ROLE_REVIEWER")) {
				 //ovdje pocetak
				 
	        	  cR.delete(clanak);

	  	        return ResponseEntity.ok().build();
			     //ovdje kraj
			 }
			else {	
					throw new AccessDeniedException("nepravilna rola");
			}
	        
	      
	    }
	 private static HttpEntity<?> getHeaders() throws IOException {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			return new HttpEntity<>(headers);
		}

}
