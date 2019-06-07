package com.example.demo.Controllers;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
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

import com.example.demo.KorisnikApplication;
import com.example.demo.Entities.Korisnik;
import com.example.demo.Entities.KorisnikPodaci;
import com.example.demo.Entities.UlogaKorisnik;
import com.example.demo.Repositories.KorisnikPodaciRepository;
import com.example.demo.Repositories.KorisnikRepository;
import com.example.demo.Repositories.UlogaKorisnikRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/korisnik")
public class KorisnikController {
	
	@Autowired
	KorisnikRepository korisnikRepo;
	@Autowired
	KorisnikPodaciRepository kPR;
	@Autowired 
	UlogaKorisnikRepository uKR;
	@Autowired
	private DiscoveryClient discoveryClient;
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@CrossOrigin
	@GetMapping(value="/testAsync")
	public String getResponse(){
		//rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.HistorijaClanak.korisnik", "1" + "IMEVELIKO");
		return "OK";

	}
	//@PreAuthorize("hasAuthority('ROLE_AUTOR')")
	@CrossOrigin
	@GetMapping(value="/")
    public List<Korisnik> getAll(@RequestHeader(value="role") String acceptHeader, HttpServletRequest request){ 
      
		return korisnikRepo.findAll();
    }
	@CrossOrigin
	@GetMapping(value="/getAllUsers")
    public List<Korisnik> getAllUsers(/*@RequestHeader(value="role") String acceptHeader, */HttpServletRequest request){ 
      
		return korisnikRepo.findAll();
    }
	 @CrossOrigin
	 @GetMapping("/{id}")
		public Korisnik getKorisnikById(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
		return korisnikRepo.findById(id).orElseThrow(() -> new NotFoundException("User with given id not found"));
	    }
	 @CrossOrigin
	 @PostMapping(value="")
	    public Korisnik createKorisnik(@RequestBody @Valid final Korisnik korisnik, Errors errors) throws Exception {
		 KorisnikPodaci kp = kPR.
	        		findById(korisnik.getKorisnikPodaci().getId())
	        		.orElseThrow(() -> new NotFoundException("User data with given id not found"));
	        UlogaKorisnik uk = uKR.
	        		findById(korisnik.getUlogaKorisnik().getId())
	        		.orElseThrow(() -> new NotFoundException("Role with given id not found"));
	     //   if(errors.hasErrors()){
	       //     throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        //}
		 /*
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
			
			String requestJson = "{\"username\":\""  + korisnik.getUsername() +  "\"}";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
				RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response=null;
			try{
				response = restTemplate.postForEntity( baseUrl, entity , String.class );
				RestTemplate restTemplate2 = new RestTemplate();
				ResponseEntity<String> response2 = restTemplate2 .postForEntity( baseUrl2, entity , String.class );
				System.out.println(response2.getBody());
			}catch (Exception ex)
			{	
				System.out.println(ex);
			}
			System.out.println(response.getBody());
			*/
	        
	        
	        //Otkomentarisati liniju ispod poslije! 28 05 2019, rekla Hanna da zakomentarišem
		 //rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "", "1" + korisnik.getUsername());
		 
		 /*rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.HistorijaClanak.korisnik", "1" + korisnik.getUsername());
		 rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.Clanak.korisnik", "1" + korisnik.getUsername());
		 rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.HistorijaClanak.korisnik", "1" + korisnik.getUsername());
		 rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.Clanak.korisnik", "1" + korisnik.getUsername());
			*/
			
	        
	        
	        

	        return korisnikRepo.save(korisnik);
	    }
	 @CrossOrigin
	 @PutMapping("/{id}")
	    public Korisnik updateKorisnik(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid Korisnik korisnikUpdate, @RequestHeader(value="role") String acceptHeader, Errors errors) throws NotFoundException, Exception {

	     //   if(errors.hasErrors()){
	       //     throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        //}
	        

	        Korisnik korisnik = korisnikRepo
	                .findById(id)
	                .orElseThrow(
	                        () -> new NotFoundException("User with given id not found")
	                );
	        KorisnikPodaci kp = kPR.
	        		findById(korisnikUpdate.getKorisnikPodaci().getId())
	        		.orElseThrow(() -> new NotFoundException("User data with given id not found"));
	        UlogaKorisnik uk = uKR.
	        		findById(korisnikUpdate.getUlogaKorisnik().getId())
	        		.orElseThrow(() -> new NotFoundException("Role with given id not found"));
	        korisnik.setUsername(korisnikUpdate.getUsername());
	        korisnik.setPassword(korisnikUpdate.getPassword());
	        korisnik.setKorisnikPodaci(korisnikUpdate.getKorisnikPodaci());
	        korisnik.setUlogaKorisnik(korisnikUpdate.getUlogaKorisnik());
	        
	        
	        /*
	        List<ServiceInstance> instances=discoveryClient.getInstances("HistorijaClanak-service");
	        List<ServiceInstance> instances2=discoveryClient.getInstances("Clanak-service");
	        
	        
	        if(instances.isEmpty()) ;
			ServiceInstance serviceInstance=instances.get(0);
			
			 if(instances2.isEmpty()) ;
				ServiceInstance serviceInstance2=instances2.get(0);
			
			String baseUrl=serviceInstance.getUri().toString()+ "/korisnik/update/"+id.toString();
			System.out.println(baseUrl);
			String baseUrl2=serviceInstance2.getUri().toString()+ "/korisnik/update/"+id.toString();
			System.out.println(baseUrl2);
			
			String requestJson = "{\"username\":\"" + korisnikUpdate.getUsername() + "\"}";
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> entity = new HttpEntity<String>(requestJson,headers);
			
			
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response=null;
			try{
				restTemplate.put(baseUrl, entity, String.class);
				RestTemplate restTemplate2 = new RestTemplate();
						restTemplate2.put(baseUrl2, entity, String.class);
				}catch (Exception ex)
			{	
				System.out.println(ex);
			}
			
	        */
	        
	        
	        
	        //rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.HistorijaClanak.korisnik","2" +Long.valueOf()+ korisnik.getUsername());
	        /*rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.HistorijaClanak.korisnik", "2" + id.toString()+ korisnikUpdate.getUsername());
	        rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.Clanak.korisnik", "2" + id.toString()+ korisnikUpdate.getUsername());
	        rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.HistorijaClanak.korisnik", "2" + id.toString()+ korisnikUpdate.getUsername());
	        rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.Clanak.korisnik", "2" + id.toString()+ korisnikUpdate.getUsername());*/
	        //rabbitTemplate.convertAndSend("spring-boot", "", "2" + id.toString()+ korisnikUpdate.getUsername());
			
	        
	        
	        korisnikUpdate = korisnikRepo.save(korisnik);
	        return korisnikUpdate;
	    }
	 @CrossOrigin
	 @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteKorisnik(@PathVariable(value = "id") Long id,@RequestHeader(value="role") String acceptHeader) throws NotFoundException {
	        Korisnik korisnik = korisnikRepo.findById(id)
	                .orElseThrow(() -> new NotFoundException("User with given id not found"));
	        
	        /*
	        List<ServiceInstance> instances=discoveryClient.getInstances("HistorijaClanak-service");
	        List<ServiceInstance> instances2=discoveryClient.getInstances("Clanak-service");
	        
	        
	        if(instances.isEmpty()) ;
			ServiceInstance serviceInstance=instances.get(0);
			
			 if(instances2.isEmpty()) ;
				ServiceInstance serviceInstance2=instances2.get(0);
			
			String baseUrl=serviceInstance.getUri().toString()+ "/korisnik/delete/" +id.toString();
			System.out.println(baseUrl);
			String baseUrl2=serviceInstance2.getUri().toString()+ "/korisnik/delete/" +id.toString();
			System.out.println(baseUrl2);
			
				
			RestTemplate restTemplate = new RestTemplate();
			ResponseEntity<String> response=null;
			try{
				response  = restTemplate.exchange(baseUrl, HttpMethod.DELETE, getHeaders(), String.class);
				RestTemplate restTemplate2 = new RestTemplate();
				ResponseEntity<String> response2 = restTemplate2.exchange(baseUrl2, HttpMethod.DELETE, getHeaders(), String.class);
				System.out.println(response2.getBody());
			}catch (Exception ex)
			{	
				System.out.println(ex);
			}
			System.out.println(response.getBody());
			*/
	        
	        rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.HistorijaClanak.korisnik","3" +Long.valueOf(id));
	        rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.Clanak.korisnik","3" +Long.valueOf(id));
	        rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.HistorijaClanak.korisnik","3" +Long.valueOf(id));
	        rabbitTemplate.convertAndSend(KorisnikApplication.topicExchangeName, "nwt.Clanak.korisnik","3" +Long.valueOf(id));
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        
	        

	        korisnikRepo.delete(korisnik);

	        return ResponseEntity.ok().build();
	    }
	 @CrossOrigin
	 private static HttpEntity<?> getHeaders() throws IOException {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			return new HttpEntity<>(headers);
		}


}

