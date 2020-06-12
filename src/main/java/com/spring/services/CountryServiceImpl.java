package com.spring.services;

import com.spring.entities.CountryEntity;
import com.spring.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryServiceInterface {


    @Autowired
    private CountryRepository repository;

    @Override
    public List<CountryEntity> findAll() {

        return (List<CountryEntity>) repository.findAll();
    }
}
