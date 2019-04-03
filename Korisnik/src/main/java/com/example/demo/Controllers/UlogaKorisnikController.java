package com.example.demo.Controllers;

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

import com.example.demo.Entities.UlogaKorisnik;
import com.example.demo.Repositories.UlogaKorisnikRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/uloga")
public class UlogaKorisnikController {
	
	@Autowired
	UlogaKorisnikRepository ulogaKorisnikRepo;
	
	@GetMapping(value="/all")
    public List<UlogaKorisnik> getAll(){
        return ulogaKorisnikRepo.findAll();
    }
	
	 @GetMapping("/get/{id}")
	    public UlogaKorisnik getUlogaById(@PathVariable(value = "id") Long id) throws NotFoundException {
	        return ulogaKorisnikRepo.findById(id).orElseThrow(() -> new NotFoundException("Uloga with given id not found"));
	    }
	 
	 @PostMapping(value="/insert")
	    public UlogaKorisnik createUloga(@RequestBody @Valid final UlogaKorisnik ulogaKorisnik, Errors errors) throws Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }

	        return ulogaKorisnikRepo.save(ulogaKorisnik);
	    }
	 
	 @PutMapping("update/{id}")
	    public UlogaKorisnik updateUloga(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid UlogaKorisnik ulogaKorisnikUpdate, Errors errors) throws NotFoundException, Exception {

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
	 
	 @DeleteMapping("delete/{id}")
	    public ResponseEntity<?> deleteUloga(@PathVariable(value = "id") Long id) throws NotFoundException {
	        UlogaKorisnik ulogaKorisnik = ulogaKorisnikRepo.findById(id)
	                .orElseThrow(() -> new NotFoundException("Uloga with given id not found"));

	        ulogaKorisnikRepo.delete(ulogaKorisnik);

	        return ResponseEntity.ok().build();
	    }


}