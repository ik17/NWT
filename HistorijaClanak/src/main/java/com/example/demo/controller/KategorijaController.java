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

import com.example.demo.entity.Kategorija;
import com.example.demo.repository.KategorijaRepository;


import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/kategorija")
public class KategorijaController {
	
	@Autowired
	KategorijaRepository kR;
	@Autowired
	private DiscoveryClient discoveryClient;
	
	@GetMapping(value="")
    public List<Kategorija> getAll(@RequestHeader(value="role") String acceptHeader){
		if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			return kR.findAll();
			 //ovdje kraj
		 }
		else {	
				throw new AccessDeniedException("nepravilna rola");
		}
		
        
    }
	
	
	 @GetMapping("/{id}")
	    public Kategorija getCategoryById(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
	     
		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			 return kR.findById(id).orElseThrow(() -> new NotFoundException("Category with given id not found"));
			   
			 //ovdje kraj
		 }
		else {	
				throw new AccessDeniedException("nepravilna rola");
		}
		 
		 }
	 
	 @PostMapping(value="")
	    public Kategorija createCategory(@RequestBody @Valid final Kategorija kategorija,@RequestHeader(value="role") String acceptHeader, Errors errors) throws Exception {

	        //if(errors.hasErrors()){
	        //    throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        //}
		 	
		 	/*List<ServiceInstance> instances = discoveryClient.getInstances("Clanak-service");
		 	if(instances.isEmpty()) ;
		 	ServiceInstance serviceInstance=instances.get(0);
		 	String baseUrl=serviceInstance.getUri().toString()+ "/kategorija"; //+id.toString();
			System.out.println(baseUrl);
			String requestJson = "{\"naziv\":\"" + kategorija.getNaziv() + "\"}";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response=null;
			
			try{
					response = restTemplate.postForEntity( baseUrl, entity , String.class );
					System.out.println(response.getBody());
				}catch (Exception ex)
				{	
					System.out.println(ex);
				}*/
		 
		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			 return kR.save(kategorija);  
			 //ovdje kraj
		 }
		else {	
				throw new AccessDeniedException("nepravilna rola");
		}
			
	       
	    }
	 
	 @PutMapping("/{id}")
	    public Kategorija updateCategory(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid Kategorija kategorijaUpdated,@RequestHeader(value="role") String acceptHeader, Errors errors) throws NotFoundException, Exception {

		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			  if(errors.hasErrors()){
		            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		        }

		        Kategorija kategorija = kR
		                .findById(id)
		                .orElseThrow(
		                        () -> new NotFoundException("Category with given id not found")
		                );
		        kategorija.setNaziv(kategorijaUpdated.getNaziv());
		      /*  List<ServiceInstance> instances = discoveryClient.getInstances("Clanak-service");
		        ServiceInstance serviceInstance = instances.get(0);
		        String baseUrl=serviceInstance.getUri().toString()+ "/kategorija/"+id.toString();
		        String requestJson = "{\"naziv\":\"" + kategorijaUpdated.getNaziv() + "\"}";
		        HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);
				HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
				RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<String> response=null;
				try{
					restTemplate.put(baseUrl, entity, String.class);
				}catch (Exception ex) {
					System.out.println(ex);
				}*/
				
				

		        kategorijaUpdated = kR.save(kategorija);
		        return kategorijaUpdated;
			 //ovdje kraj
		 }
		else {	
				throw new AccessDeniedException("nepravilna rola");
		}
		 
		 
		 
	      
	    }
	 @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
	     
		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			 Kategorija kategorija = kR.findById(id)
		                .orElseThrow(() -> new NotFoundException("Category with given id not found"));
		      /*  List<ServiceInstance> instances = discoveryClient.getInstances("Clanak-service");
		        ServiceInstance serviceInstance=instances.get(0);
		        String baseUrl=serviceInstance.getUri().toString()+ "/kategorija/" +id.toString();
		        RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<String> response=null;
				try{
					//response=restTemplate.exchange(baseUrl,HttpMethod.POST, getHeaders(),String.class);
						response  = restTemplate.exchange(baseUrl, HttpMethod.DELETE, getHeaders(), String.class);
					}catch (Exception ex) {
						System.out.println(ex);
					}
					System.out.println(response.getBody());
		        */
		        kR.delete(kategorija);

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
