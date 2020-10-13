package com.fcr.integracaopetzbackrest.endpoint;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

//import br.com.devdojo.error.ResourceNotFoundException;

@RestController
@RequestMapping("clientes")
public class ClienteEndpoint {

	private final ClienteRepository clienteDao;

	@Autowired
	public ClienteEndpoint(ClienteRepository clienteDao) {
		this.clienteDao = clienteDao;
	}

	@GetMapping
	public ResponseEntity<?> listAll() {
		return new ResponseEntity<>(clienteDao.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<?> getClienteById(@PathVariable("id") Long id) {
		verifyIfClienteExists(id);
		Cliente cliente = clienteDao.findById(id).get();
		return new ResponseEntity<>(cliente, HttpStatus.OK);
	}

	@GetMapping(path = "/findByName/{name}")
	public ResponseEntity<?> findClientesByName(@PathVariable String name) {
		return new ResponseEntity<>(clienteDao.findByNomeIgnoreCaseContaining(name), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> save(@Valid @RequestBody Cliente cliente) {
		return new ResponseEntity<>(clienteDao.save(cliente), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		verifyIfClienteExists(id);
		clienteDao.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping
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
