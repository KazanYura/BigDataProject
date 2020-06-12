package com.spring.controllers;

import com.neovisionaries.i18n.CountryCode;
import com.spring.entities.CityEntity;
import com.spring.entities.CountryEntity;
import com.spring.requests.CityRequest;
import com.spring.services.CityServiceInterface;
import com.spring.services.CountryServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CountryController {

    @Autowired
    private CountryServiceInterface countryServiceInterface;
    @Autowired
    private CityServiceInterface cityServiceInterface;
    @GetMapping("/showCountries")
    public ResponseEntity showCountries() {
        ArrayList<String> countriesNames = new ArrayList<>();
        List<CountryEntity> countries = countryServiceInterface.findAll();
        for (CountryEntity c:countries) {
            CountryCode code = CountryCode.getByCode(c.getCountryName().toUpperCase());
            countriesNames.add(code.getName());
        }
        return ResponseEntity.ok(countriesNames.toArray());
    }
    @PostMapping("/getCities")
    public ResponseEntity getCities(@RequestBody CityRequest countryName){
        CountryCode code = CountryCode.findByName(countryName.getCountryName()).get(0);
        ArrayList<String> cityNames = new ArrayList<>();
        List<CityEntity> cityEntities = cityServiceInterface.findCityEntityByCityName(code.name());
        for (CityEntity city:cityEntities) {
            cityNames.add(city.getCityName());
        }
        return ResponseEntity.ok(cityNames.toArray());
    }
}
