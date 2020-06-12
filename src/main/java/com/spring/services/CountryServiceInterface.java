package com.spring.services;

import com.spring.entities.CountryEntity;

import java.util.List;

public interface CountryServiceInterface {

    List<CountryEntity> findAll();
}