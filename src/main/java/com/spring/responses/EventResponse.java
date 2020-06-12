package com.spring.responses;

public class EventResponse {
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

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
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

    public String[] getTopics() {
        return topics;
    }

    public void setTopics(String[] topics) {
        this.topics = topics;
    }

    public EventResponse(int ID, String eventName, String eventTime, String groupName, String cityName, String countryName, String[] topics) {
        this.ID = ID;
        this.eventName = eventName;
        this.eventTime = eventTime;
        this.groupName = groupName;
        this.cityName = cityName;
        this.countryName = countryName;
        this.topics = topics;
    }

    private int ID;
    private String eventName;
    private String eventTime;
    private String groupName;
    private String cityName;
    private String countryName;
    private String[] topics;

}
