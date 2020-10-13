package com.fcr.integracaopetzbackrest.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.fcr.integracaopetzbackrest.model.Cliente;

public interface ClienteRepository extends CrudRepository<Cliente, Long> {

	List<Cliente> findByNomeIgnoreCaseContaining(String name);
}
