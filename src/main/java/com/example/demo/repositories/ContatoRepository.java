package com.example.demo.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.model.Contato;

public interface ContatoRepository extends CrudRepository<Contato, Long>{

	@Override
	public List<Contato> findAll();
	
	
}
