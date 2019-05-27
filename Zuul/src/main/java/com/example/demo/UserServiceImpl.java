package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.models.LoginUser;

//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static java.util.Collections.emptyList;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService {
   @Autowired
   private UserRepository userDao;   
   @Autowired
   private BCryptPasswordEncoder passwordEncoder;   
   @Autowired
   private DiscoveryClient discoveryClient;
  // @Override
   public User save(LoginUser user) {
         User newUser = new User(user.getUsername(),passwordEncoder.encode(user.getPassword()),user.getRole());
         
     	List<ServiceInstance> instances=discoveryClient.getInstances("Korisnik-service");
     
        if(instances.isEmpty()) System.out.println( "ne radi servis");
		ServiceInstance serviceInstance=instances.get(0);
		System.out.println(serviceInstance.getUri().toString());
		String baseUrlKorisnik = serviceInstance.getUri().toString()+ "/korisnik"; //+id.toString();
		String baseUrlPodaci = serviceInstance.getUri().toString()+ "/podaci";
		String baseUrlUloge = serviceInstance.getUri().toString()+ "/uloga";
		
		String username = user.getUsername();
		String password = passwordEncoder.encode(user.getPassword());
		String bio = user.getBiografija();
		String ime = user.getIme();
		String prezime = user.getPrezime();
		String rola = user.getRole();
		
		
		String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

		String date = simpleDateFormat.format(new Date());
		
		String requestJsonRola= "{\"ulogaKorisnik\":\"" + rola + "\"}}";
		String requestJsonKorisnickiPodaci = "{\"ime\":\"" + ime + "\",\"prezime\":\"" + prezime + "\",\"biografija\":\"" + bio + "\",\"datumPrijave\":\"" + date + "\"}";
		//String requestJson = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}}"; 
		//ovaj ide zadnji, i u njega idu ova dva response-a

		System.out.println(requestJsonKorisnickiPodaci);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<String>(requestJsonRola, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseRola=null;
		try{
			responseRola = restTemplate.postForEntity( baseUrlUloge, entity , String.class );
			System.out.println(responseRola.getBody());
			}catch (Exception ex)
		{	
			System.out.println(ex);
		}
		
		HttpHeaders headers2 = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity2 = new HttpEntity<String>(requestJsonKorisnickiPodaci, headers);
		RestTemplate restTemplate2 = new RestTemplate();
		ResponseEntity<String> responseKP = null;
		try{
			responseKP = restTemplate.postForEntity( baseUrlPodaci, entity2 , String.class );
			System.out.println(responseKP.getBody());
			}catch (Exception ex)
		{	
			System.out.println(ex);
		}
     
		HttpHeaders headers3 = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		
		String requestJson = "{\"username\":\"" + username + "\",\"password\":\"" + password + "\",\"korisnikPodaci\":" + responseKP.getBody() + ",\"ulogaKorisnik\":" + responseRola.getBody() + "}"; 
		System.out.println(requestJson);
		HttpEntity<String> entity3 = new HttpEntity<String>(requestJson, headers);
		RestTemplate restTemplate3 = new RestTemplate();
		ResponseEntity<String> responseKorisnik = null;
		try{
			responseKorisnik = restTemplate.postForEntity( baseUrlKorisnik, entity3 , String.class );
			}catch (Exception ex)
		{	
			System.out.println(ex);
		}
		return userDao.save(newUser);
    }
   public User findOne(String username) {
	   return userDao.findOne(username);
   }
   public UserDetails loadUserByUsername(String userId) throws
               UsernameNotFoundException {
         User user = userDao.findByUsername(userId);
         if(user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
         }
       //TODO: Get user by username from users microservice, and change emptyList() with user role
         return new org.springframework.security.core.userdetails.User(
            user.getUsername(), user.getPassword(), emptyList());
         }
  // Other service methods
}