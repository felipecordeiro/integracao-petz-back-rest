package com.fcr.integracaopetzbackrest.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.fcr.integracaopetzbackrest.model.Cliente;

public interface ClienteRepository extends PagingAndSortingRepository<Cliente, Long> {

	List<Cliente> findByNomeIgnoreCaseContaining(String name);
}
