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

import com.example.demo.entity.Autor;
import com.example.demo.repository.AutorRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/autor")
public class AutorController {
	@Autowired
	AutorRepository aR;
	
	@GetMapping(value="/all")
    public List<Autor> getAll(){
        return aR.findAll();
    }
	
	 @GetMapping("/get/{id}")
	    public Autor getAutorById(@PathVariable(value = "id") Long id) throws NotFoundException {
	        return aR.findById(id).orElseThrow(() -> new NotFoundException("Autor with given id not found"));
	    }
	 
	 @PostMapping(value="/insert")
	    public Autor createAutor(@RequestBody @Valid final Autor autor, Errors errors) throws Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }

	        return aR.save(autor);
	    }
	 
	 @PutMapping("update/{id}")
	    public Autor updateAutor(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid Autor autorUpdated, Errors errors) throws NotFoundException, Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }

	        Autor autor = aR
	                .findById(id)
	                .orElseThrow(
	                        () -> new NotFoundException("Autor with given id not found")
	                );
	        
	        if(autorUpdated.getIdClanak() != null) {
	        autor.setIdClanak(autorUpdated.getIdClanak());
	        }
	        if(autorUpdated.getIdKorisnik() != null) {
	        	autor.setIdKorisnik(autorUpdated.getIdKorisnik());
		        }
	       
	       	        
	        
	  
	       

	        autorUpdated = aR.save(autor);
	        return autorUpdated;
	    }
	 @DeleteMapping("delete/{id}")
	    public ResponseEntity<?> deleteAutor(@PathVariable(value = "id") Long id) throws NotFoundException {
	        Autor autor = aR.findById(id)
	                .orElseThrow(() -> new NotFoundException("Autor with given id not found"));

	        aR.delete(autor);

	        return ResponseEntity.ok().build();
	    }
}
