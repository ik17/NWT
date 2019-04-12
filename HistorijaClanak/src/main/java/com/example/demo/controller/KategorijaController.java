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

import com.example.demo.entity.Kategorija;
import com.example.demo.repository.KategorijaRepository;

import javassist.NotFoundException;

@RestController
@RequestMapping(value = "/kategorija")
public class KategorijaController {
	
	@Autowired
	KategorijaRepository kR;
	
	@GetMapping(value="")
    public List<Kategorija> getAll(){
        return kR.findAll();
    }
	
	 @GetMapping("/{id}")
	    public Kategorija getCategoryById(@PathVariable(value = "id") Long id) throws NotFoundException {
	        return kR.findById(id).orElseThrow(() -> new NotFoundException("Category with given id not found"));
	    }
	 
	 @PostMapping(value="")
	    public Kategorija createCategory(@RequestBody @Valid final Kategorija kategorija, Errors errors) throws Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }

	        return kR.save(kategorija);
	    }
	 
	 @PutMapping("/{id}")
	    public Kategorija updateCategory(@PathVariable(value = "id") Long id,
	                                               @RequestBody @Valid Kategorija kategorijaUpdated, Errors errors) throws NotFoundException, Exception {

	        if(errors.hasErrors()){
	            throw new Exception(errors.getAllErrors().get(0).getDefaultMessage());
	        }

	        Kategorija kategorija = kR
	                .findById(id)
	                .orElseThrow(
	                        () -> new NotFoundException("Category with given id not found")
	                );
	        kategorija.setNaziv(kategorijaUpdated.getNaziv());
	        
	       

	        kategorijaUpdated = kR.save(kategorija);
	        return kategorijaUpdated;
	    }
	 @DeleteMapping("/{id}")
	    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") Long id) throws NotFoundException {
	        Kategorija kategorija = kR.findById(id)
	                .orElseThrow(() -> new NotFoundException("Category with given id not found"));

	        kR.delete(kategorija);

	        return ResponseEntity.ok().build();
	    }


}
