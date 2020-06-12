package com.spring.entities;

import javax.persistence.*;

@Entity
@Table(name = "eventtable")
public class EventEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int ID;
    @Column(name="eventName")
    private String eventName;
    @Column(name="eventTime")
    private long eventTime;
    @Column(name="groupName")
    private String groupName;
    @Column(name="cityName")
    private String cityName;
    @Column(name="countryName")
    private String countryName;
    @Column(name="topics")
    private String topics;

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getEventTime() {
        return eventTime;
    }

    public void setEventTime(long eventTime) {
        this.eventTime = eventTime;
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

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
