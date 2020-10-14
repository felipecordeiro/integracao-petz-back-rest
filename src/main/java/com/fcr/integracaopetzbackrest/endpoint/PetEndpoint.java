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
import com.fcr.integracaopetzbackrest.model.Pet;
import com.fcr.integracaopetzbackrest.repository.PetRepository;

@RestController
@RequestMapping("v1")
public class PetEndpoint {

	private final PetRepository petDao;

	@Autowired
	public PetEndpoint(PetRepository petDao) {
		this.petDao = petDao;
	}

	@GetMapping(path = "pets")
	public ResponseEntity<?> listAll(Pageable pageable) {
		return new ResponseEntity<>(petDao.findAll(pageable), HttpStatus.OK);
	}

	@GetMapping(path = "pets/{id}")
	public ResponseEntity<?> getPetById(@PathVariable("id") Long id) {
		verifyIfPetExists(id);
		Pet pet = petDao.findById(id).get();
		return new ResponseEntity<>(pet, HttpStatus.OK);
	}

	@PostMapping(path = "pets")
	public ResponseEntity<?> save(@Valid @RequestBody Pet pet) {
		return new ResponseEntity<>(petDao.save(pet), HttpStatus.CREATED);
	}

	@DeleteMapping(path = "pets/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		verifyIfPetExists(id);
		petDao.deleteById(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping(path = "pets")
	public ResponseEntity<?> update(@RequestBody Pet pet) {
		verifyIfPetExists(pet.getId());
		petDao.save(pet);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	private void verifyIfPetExists(Long id) {
		if (!petDao.findById(id).isPresent()) {
			throw new ResourceNotFoundException("Pet not found for ID: " + id);
		}
	}
}
