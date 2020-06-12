package com.database.containers;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class WindowPerMinuteContainer<T> {
    public long time;
    public String countries;
    public WindowPerMinuteContainer(long timestamp, HashMap<String,T> countries) {
        JSONObject object = new JSONObject(countries);
        this.countries = object.toString();
        this.time = timestamp;
    }
}