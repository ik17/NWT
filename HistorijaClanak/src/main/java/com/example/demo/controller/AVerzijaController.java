package com.example.demo.controller;

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
	
	@GetMapping(value="/all")
    public List<AVerzija> getAll(){
        return vR.findAll();
    }
	
	 @GetMapping("/get/{id}")
	    public AVerzija getVersionById(@PathVariable(value = "id") Long id) throws NotFoundException {
	        return vR.findById(id).orElseThrow(() -> new NotFoundException("Version with given id not found"));
	    }
	 
	 @PostMapping(value="/insert")
	    public AVerzija createVersion(@RequestBody @Valid final AVerzija verzija, Errors errors) throws Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }
	        Clanak clanak = cR
	                .findById(verzija.getIdClanak().getId())
	                .orElseThrow(
	                        () -> new NotFoundException("Article with given id not found")
	                );

	        return vR.save(verzija);
	    }
	 
	 @PutMapping("update/{id}")
	    public AVerzija updateVersion(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid AVerzija verzijaUpdated, Errors errors) throws NotFoundException, Exception {

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
	    }
	 @DeleteMapping("delete/{id}")
	    public ResponseEntity<?> deleteVersion(@PathVariable(value = "id") Long id) throws NotFoundException {
	        AVerzija verzija = vR.findById(id)
	                .orElseThrow(() -> new NotFoundException("Version with given id not found"));

	        vR.delete(verzija);

	        return ResponseEntity.ok().build();
	    }

}
