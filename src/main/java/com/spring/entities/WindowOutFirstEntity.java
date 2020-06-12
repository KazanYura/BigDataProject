package com.spring.entities;

import javax.persistence.*;

@Entity
@Table(name="windowstorage")
public class WindowOutFirstEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long timestampID;

    public long getTimestampID() {
        return timestampID;
    }

    public void setTimestampID(long timestampID) {
        this.timestampID = timestampID;
    }

    public String getCountriesJSON() {
        return countriesJSON;
    }

    public void setCountriesJSON(String countriesJSON) {
        this.countriesJSON = countriesJSON;
    }

    @Column(name="countriesJSON")
    private String countriesJSON;
}
