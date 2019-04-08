package com.example.demo.Controllers;

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
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

import com.example.demo.Entities.Korisnik;
import com.example.demo.Repositories.KorisnikRepository;


import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/korisnik")
public class KorisnikController {
	
	@Autowired
	KorisnikRepository korisnikRepo;
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@GetMapping(value="/all")
    public List<Korisnik> getAll(){
        return korisnikRepo.findAll();
    }
	
	 @GetMapping("/get/{id}")
	    public Korisnik getKorisnikById(@PathVariable(value = "id") Long id) throws NotFoundException {
	        return korisnikRepo.findById(id).orElseThrow(() -> new NotFoundException("User with given id not found"));
	    }
	 
	 @PostMapping(value="/insert")
	    public Korisnik createKorisnik(@RequestBody @Valid final Korisnik korisnik, Errors errors) throws Exception {

	     //   if(errors.hasErrors()){
	       //     throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        //}
	        List<ServiceInstance> instances=discoveryClient.getInstances("HistorijaClanak-service");
	        List<ServiceInstance> instances2=discoveryClient.getInstances("Clanak-service");
	        
	        
	        if(instances.isEmpty()) ;
			ServiceInstance serviceInstance=instances.get(0);
			
			 if(instances2.isEmpty()) ;
				ServiceInstance serviceInstance2=instances2.get(0);
			
			String baseUrl=serviceInstance.getUri().toString()+ "/korisnik/insert"; //+id.toString();
			System.out.println(baseUrl);
			String baseUrl2=serviceInstance2.getUri().toString()+ "/korisnik/insert"; //+id.toString();
			System.out.println(baseUrl2);
			
			String requestJson = "{\"username\":\"Komunikacija\"}";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			//String answer = restTemplate.postForObject(url, entity, String.class);

			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response=null;
			try{
			//response=restTemplate.exchange(baseUrl,HttpMethod.POST, getHeaders(),String.class);
				response = restTemplate.postForEntity( baseUrl, entity , String.class );
				RestTemplate restTemplate2 = new RestTemplate();
				ResponseEntity<String> response2 = restTemplate2 .postForEntity( baseUrl2, entity , String.class );
				System.out.println(response2.getBody());
			}catch (Exception ex)
			{	///ovdje if(contains null) return NEMA
				// ex.getMessage();
				//return ex.getCause().toString();
				System.out.println(ex);
			}
			System.out.println(response.getBody());
			
			//return response.getBody();
	        
	        
	        

	        return korisnikRepo.save(korisnik);
	    }
	 
	 @PutMapping("update/{id}")
	    public Korisnik updateKorisnik(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid Korisnik korisnikUpdate, Errors errors) throws NotFoundException, Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }
	        

	        Korisnik korisnik = korisnikRepo
	                .findById(id)
	                .orElseThrow(
	                        () -> new NotFoundException("User with given id not found")
	                );
	        
	        korisnik.setUsername(korisnikUpdate.getUsername());
	        korisnik.setPassword(korisnikUpdate.getPassword());
	        korisnik.setKorisnikPodaci(korisnikUpdate.getKorisnikPodaci());
	        korisnik.setUlogaKorisnik(korisnikUpdate.getUlogaKorisnik());
	        
	        korisnikUpdate = korisnikRepo.save(korisnik);
	        return korisnikUpdate;
	    }
	 
	 @DeleteMapping("delete/{id}")
	    public ResponseEntity<?> deleteKorisnik(@PathVariable(value = "id") Long id) throws NotFoundException {
	        Korisnik korisnik = korisnikRepo.findById(id)
	                .orElseThrow(() -> new NotFoundException("User with given id not found"));

	        korisnikRepo.delete(korisnik);

	        return ResponseEntity.ok().build();
	    }
	 
	 private static HttpEntity<?> getHeaders() throws IOException {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			return new HttpEntity<>(headers);
		}


}

