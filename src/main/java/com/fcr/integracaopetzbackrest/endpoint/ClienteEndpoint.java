package com.fcr.integracaopetzbackrest.endpoint;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fcr.integracaopetzbackrest.error.ResourceNotFoundException;
import com.fcr.integracaopetzbackrest.model.Cliente;
import com.fcr.integracaopetzbackrest.repository.ClienteRepository;

@RestController
@RequestMapping("v1")
public class ClienteEndpoint {

	private final ClienteRepository clienteDao;

	@Autowired
	public ClienteEndpoint(ClienteRepository clienteDao) {
		this.clienteDao = clienteDao;
	}

	@GetMapping(path = "clientes")
	public ResponseEntity<?> listAll(Pageable pageable) {
		return new ResponseEntity<>(clienteDao.findAll(pageable), HttpStatus.OK);
	}

	@GetMapping(path = "clientes/{id}")
	public ResponseEntity<?> getClienteById(@PathVariable("id") Long id) {
		verifyIfClienteExists(id);
		Cliente cliente = clienteDao.findById(id).get();
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@GetMapping(path = "clientes/findByNome/{nome}")
	public ResponseEntity<?> findClientesByName(@PathVariable String nome) {
		return new ResponseEntity<>(clienteDao.findByNomeIgnoreCaseContaining(nome), HttpStatus.OK);
	}

	@PostMapping(path = "clientes")
	public ResponseEntity<?> save(@Valid @RequestBody Cliente cliente) {
		return new ResponseEntity<>(clienteDao.save(cliente), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "clientes/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		verifyIfClienteExists(id);
		clienteDao.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(path = "clientes")
	public ResponseEntity<?> update(@RequestBody Cliente cliente) {
		verifyIfClienteExists(cliente.getId());
		clienteDao.save(cliente);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private void verifyIfClienteExists(Long id) {
		if (!clienteDao.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Cliente not found for ID: " + id);
		}
	}
}
