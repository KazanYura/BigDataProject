package com.spring.entities;

import javax.persistence.*;

@Entity
@Table(name = "grouptable")
public class GroupEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    @Column(name = "groupName")
    private String groupName;
    @Column(name = "cityName")
    private String cityName;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}