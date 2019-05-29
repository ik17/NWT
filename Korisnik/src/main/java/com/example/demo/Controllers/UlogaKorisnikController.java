package com.example.demo.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.example.demo.Entities.UlogaKorisnik;
import com.example.demo.Repositories.UlogaKorisnikRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/uloga")
public class UlogaKorisnikController {
	
	@Autowired
	UlogaKorisnikRepository ulogaKorisnikRepo;
	

	//@PreAuthorize("hasAuthority('ROLE_AUTOR')")
	@GetMapping(value="/getAll")
    public List<UlogaKorisnik> getAll(@RequestHeader(value="role") String acceptHeader){
        return ulogaKorisnikRepo.findAll();
    }
	
	 @GetMapping("/{id}")
	    public UlogaKorisnik getUlogaById(@PathVariable(value = "id") Long id,@RequestHeader(value="role") String acceptHeader) throws NotFoundException {
	        return ulogaKorisnikRepo.findById(id).orElseThrow(() -> new NotFoundException("Uloga with given id not found"));
	    }
	 
	 @PostMapping(value="")
	    public UlogaKorisnik createUloga(@RequestBody @Valid final UlogaKorisnik ulogaKorisnik, Errors errors) throws Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }
	        UlogaKorisnik pomocnaUloga = findByUsername(ulogaKorisnik.getUlogaKorisnik());
	        if(pomocnaUloga == null)
	        	return ulogaKorisnikRepo.save(ulogaKorisnik);
	        else return pomocnaUloga;
	    }
	 
	 @PutMapping("/{id}")
	    public UlogaKorisnik updateUloga(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid UlogaKorisnik ulogaKorisnikUpdate, @RequestHeader(value="role") String acceptHeader,Errors errors) throws NotFoundException, Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }

	        UlogaKorisnik ulogaKorisnik = ulogaKorisnikRepo
	                .findById(id)
	                .orElseThrow(
	                        () -> new NotFoundException("Uloga with given id not found")
	                );
	        
	        ulogaKorisnik.setUlogaKorisnik(ulogaKorisnikUpdate.getUlogaKorisnik());
	        
	        ulogaKorisnikUpdate = ulogaKorisnikRepo.save(ulogaKorisnik);
	        return ulogaKorisnikUpdate;
	    }
	 
	 @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteUloga(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
	        UlogaKorisnik ulogaKorisnik = ulogaKorisnikRepo.findById(id)
	                .orElseThrow(() -> new NotFoundException("Uloga with given id not found"));

	        ulogaKorisnikRepo.delete(ulogaKorisnik);

	        return ResponseEntity.ok().build();
	    }
	 public UlogaKorisnik findByUsername(String username) {
		 UlogaKorisnik uk = ulogaKorisnikRepo.findByUsername(username);
		 return uk;
	 }
	 

}