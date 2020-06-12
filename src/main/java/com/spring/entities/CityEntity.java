package com.spring.entities;

import javax.persistence.*;

@Entity
@Table(name="cities")
public class CityEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Column(name = "cityName")
    private String cityName;

}
