package com.spring.services;

import com.spring.entities.CityEntity;

import java.util.List;

public interface CityServiceInterface {

    List<CityEntity> findCityEntityByCityName(String name);
}