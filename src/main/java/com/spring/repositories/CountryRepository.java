package com.spring.repositories;

import com.spring.entities.CountryEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends CrudRepository<CountryEntity, Integer> {

}

