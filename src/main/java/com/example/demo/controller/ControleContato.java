package com.example.demo.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Contato;
import com.example.demo.repositories.ContatoRepository;

@RestController
public class ControleContato {

	@Autowired
	private ContatoRepository repo;

	@CrossOrigin("http://localhost:4200")
	@GetMapping("/todos")
	public @ResponseBody List<Contato> findAll() {
		return this.repo.findAll();
	}

	@GetMapping("/todos/{id}")
	public Contato findById(@PathVariable(value = "id") long id) {
		return this.repo.findById(id).orElse(null);
	}

	@PostMapping("/add")
	public ResponseEntity<Object> add(@RequestBody Contato contato) {

		Contato c = this.repo.save(contato);

		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().buildAndExpand(c.getId()).toUri();

		return ResponseEntity.created(uri).build();

	}

	@DeleteMapping("/remove/{id}")
	public void remove(@PathVariable(value = "id") long id) {
		this.repo.delete(this.repo.findById(id).get());
	}

	@PutMapping("all/{id}")
	public ResponseEntity<Object> update(@PathVariable (value = "id") long id,
			@RequestBody Contato newContato) {
		
		Optional<Contato> optional = this.repo.findById(id);
		
		if(!optional.isPresent())
			return ResponseEntity.notFound().build();
		
		newContato.setId(id);
		this.repo.save(newContato);
		return ResponseEntity.noContent().build();
	}
}
