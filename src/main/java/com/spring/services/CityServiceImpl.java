package com.spring.services;

import com.spring.entities.CityEntity;
import com.spring.entities.CountryEntity;
import com.spring.repositories.CityRepository;
import com.spring.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityServiceInterface {


    @Autowired
    private CityRepository repository;


    @Override
    public List<CityEntity> findCityEntityByCityName(String name) {
        return repository.findCityEntityByCityName(name);
    }
}
