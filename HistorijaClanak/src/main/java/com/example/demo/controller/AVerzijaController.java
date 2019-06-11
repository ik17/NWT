package com.example.demo.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

import com.example.demo.entity.AVerzija;
import com.example.demo.entity.Clanak;
import com.example.demo.repository.AVerzijaRepository;
import com.example.demo.repository.ClanakRepository;

import javassist.NotFoundException;


@RestController
@RequestMapping(value = "/verzija")
public class AVerzijaController {
	@Autowired
	AVerzijaRepository vR;
	@Autowired
	ClanakRepository cR;
	@Autowired
	private DiscoveryClient discoveryClient;
	@CrossOrigin
	@GetMapping(value="")
    public List<AVerzija> getAll(@RequestHeader(value="role") String acceptHeader){
		
		if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak

			 return vR.findAll();
			 //ovdje kraj
		 }
		else {
					
				throw new AccessDeniedException("nepravilna rola");
		}
    }
	@CrossOrigin
	 @GetMapping("/{id}")
	    public AVerzija getVersionById(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak

			 return vR.findById(id).orElseThrow(() -> new NotFoundException("Version with given id not found"));
			    //ovdje kraj
		 }
		else {
					
				throw new AccessDeniedException("nepravilna rola");
		}	 
		 }
	@CrossOrigin
	 @GetMapping("/prihvacen/{id}")
	    public String prihvatiClanak(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
	       
		 
		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
 
			 AVerzija v1 =  vR.findById(id).orElseThrow(() -> new NotFoundException("Version with given id not found"));
		        Optional<Clanak> c1 = cR.findById(v1.getIdClanak().getId());
		        
		        if(c1==null) return "nema clanka";
		        
		        List<ServiceInstance> instances=discoveryClient.getInstances("Clanak-service");
		             
		        if(instances.isEmpty()) return "ne radi servis";
				ServiceInstance serviceInstance=instances.get(0);
				String linkNaClanak = v1.getLink();
				String baseUrl=serviceInstance.getUri().toString()+ "/clanak/"; //+id.toString();
				System.out.println(baseUrl);
				String naziv = c1.get().getNaziv();
				String clanakOdobren = "true";
				Long idKorisnik = c1.get().getOdobrioClanak().getId();
				String imeKorisnik = c1.get().getOdobrioClanak().getUsername();
				Long idKategorije = c1.get().getIdKategorije().getId();
				String nazivKategorije = c1.get().getIdKategorije().getNaziv();
				String requestJson = "{\"naziv\":\"" + naziv + "\",\"clanakOdobren\":\"" + clanakOdobren + "\",\"odobrioClanak\":"+ "{\"id\":\"" + idKorisnik + "\",\"username\":\"" + imeKorisnik + "\"},\"kategorija\":" + "{\"id\":\"" + idKategorije + "\",\"naziv\":\"" + nazivKategorije + "\"},\"linkNaClanak\":\"" + linkNaClanak + "\"}"; 
				System.out.println(requestJson);
				HttpHeaders headers = new HttpHeaders();
				headers.setContentType(MediaType.APPLICATION_JSON);

				HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
					RestTemplate restTemplate = new RestTemplate();
				ResponseEntity<String> response=null;
				try{
					response = restTemplate.postForEntity( baseUrl, entity , String.class );
					}catch (Exception ex)
				{	
					System.out.println(ex);
				}
				System.out.println(response);
				if (response != null) return  response.getBody();
				else return "Error";
			 //ovdje kraj
		 }
		else {
					
				throw new AccessDeniedException("nepravilna rola");
		}	     
	        
	 }
	@CrossOrigin
	 @PostMapping(value="")
	    public AVerzija createVersion(@RequestBody @Valid final AVerzija verzija,@RequestHeader(value="role") String acceptHeader, Errors errors) throws Exception {

		 
		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			 if(errors.hasErrors()){
		            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		        }
		        Clanak clanak = cR
		                .findById(verzija.getIdClanak().getId())
		                .orElseThrow(
		                        () -> new NotFoundException("Article with given id not found")
		                );

		        return vR.save(verzija);
			    //ovdje kraj
		 }
		else {
					
				throw new AccessDeniedException("nepravilna rola");
		}
	       
	    }
	@CrossOrigin
	 @PutMapping("/{id}")
	    public AVerzija updateVersion(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid AVerzija verzijaUpdated,@RequestHeader(value="role") String acceptHeader, Errors errors) throws NotFoundException, Exception {

		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			 if(errors.hasErrors()){
		            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
		        }

		        AVerzija verzija = vR
		                .findById(id)
		                .orElseThrow(
		                        () -> new NotFoundException("Version with given id not found")
		                );
		        Clanak clanak = cR
		                .findById(verzijaUpdated.getIdClanak().getId())
		                .orElseThrow(
		                        () -> new NotFoundException("Article with given id not found")
		                );
		        
		        verzija.setIdClanak(clanak);
		        if (verzijaUpdated.getLink() != null) {
		        	verzija.setLink(verzijaUpdated.getLink());
			        }
		        if (verzijaUpdated.getReview() != null) {
		        	verzija.setReview(verzijaUpdated.getReview());
			        }

		        verzijaUpdated = vR.save(verzija);
		        return verzijaUpdated;

			    //ovdje kraj
		 }
		else {
					
				throw new AccessDeniedException("nepravilna rola");
		}
		 
		 
		 
	        
	    }
	@CrossOrigin
	 @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteVersion(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
	       
		 
		 if (acceptHeader.equals("ROLE_REVIEWER")) {
			 //ovdje pocetak
			
			 AVerzija verzija = vR.findById(id)
		                .orElseThrow(() -> new NotFoundException("Version with given id not found"));

		        vR.delete(verzija);

		        return ResponseEntity.ok().build();
			    //ovdje kraj
		 }
		else {
					
				throw new AccessDeniedException("nepravilna rola");
		}
		 
		 
		 
		 
		
	    }

}
