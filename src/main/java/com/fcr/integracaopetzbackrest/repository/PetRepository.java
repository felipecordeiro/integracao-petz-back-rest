package com.fcr.integracaopetzbackrest.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.fcr.integracaopetzbackrest.model.Pet;

public interface PetRepository extends PagingAndSortingRepository<Pet, Long> {

}
