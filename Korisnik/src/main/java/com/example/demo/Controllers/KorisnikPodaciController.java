package com.example.demo.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import com.example.demo.Entities.KorisnikPodaci;
import com.example.demo.Repositories.KorisnikPodaciRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/podaci")
public class KorisnikPodaciController {
	

	@Autowired
	KorisnikPodaciRepository korisnikPodaciRepo;
	
	@CrossOrigin
	@GetMapping(value="")
    public List<KorisnikPodaci> getAll(@RequestHeader(value="role") String acceptHeader){
        return korisnikPodaciRepo.findAll();
    }
	@CrossOrigin
	 @GetMapping("/{id}")
	    public KorisnikPodaci getPodaciById(@PathVariable(value = "id") Long id, @RequestHeader(value="role") String acceptHeader) throws NotFoundException {
	        return korisnikPodaciRepo.findById(id).orElseThrow(() -> new NotFoundException("korisnikPodaci with given id not found"));
	    }
	@CrossOrigin
	 @PostMapping(value="")
	    public KorisnikPodaci createPodaci(@RequestBody @Valid final KorisnikPodaci korisnikPodaci, Errors errors) throws Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }

	        return korisnikPodaciRepo.save(korisnikPodaci);
	    }
	@CrossOrigin
	 @PutMapping("/{id}")
	    public KorisnikPodaci updatePodaci(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid KorisnikPodaci korisnikPodaciUpdate,@RequestHeader(value="role") String acceptHeader, Errors errors) throws NotFoundException, Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }

	        KorisnikPodaci korisnikPodaci = korisnikPodaciRepo
	                .findById(id)
	                .orElseThrow(
	                        () -> new NotFoundException("korisnikPodaci with given id not found")
	                );
	        
	        korisnikPodaci.setIme(korisnikPodaciUpdate.getIme());
	        korisnikPodaci.setPrezime(korisnikPodaciUpdate.getPrezime());
	        korisnikPodaci.setBiografija(korisnikPodaciUpdate.getBiografija());
	        korisnikPodaci.setDatumPrijave(korisnikPodaciUpdate.getDatumPrijave());
	        
	        korisnikPodaciUpdate = korisnikPodaciRepo.save(korisnikPodaci);
	        return korisnikPodaciUpdate;
	    }
	@CrossOrigin
	 @DeleteMapping("/{id}")
	    public ResponseEntity<?> deletePodaci(@PathVariable(value = "id") Long id,@RequestHeader(value="role") String acceptHeader) throws NotFoundException {
	        KorisnikPodaci korisnikPodaci = korisnikPodaciRepo.findById(id)
	                .orElseThrow(() -> new NotFoundException("korisnikPodaci with given id not found"));

	        korisnikPodaciRepo.delete(korisnikPodaci);

	        return ResponseEntity.ok().build();
	    }


}