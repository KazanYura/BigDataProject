package com.spring.repositories;

import com.spring.entities.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CityRepository extends JpaRepository<CityEntity, Long> {
    @Query(value = "SELECT cities.ID,cities.cityName FROM cities left join countries on cities.countryID = countries.ID where countryName=?1",nativeQuery = true)
    List<CityEntity> findCityEntityByCityName(String name);
}
