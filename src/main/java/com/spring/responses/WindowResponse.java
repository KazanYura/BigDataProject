package com.spring.responses;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class WindowResponse {
    public String startTime;
    public String endTime;
    public Map<String, Integer> statistic;
    public WindowResponse(long startTime, long endTime, HashMap<String,Integer> statistic) throws JsonProcessingException {
        this.startTime = new SimpleDateFormat("HH:mm").format(startTime);
        this.endTime = new SimpleDateFormat("HH:mm").format(endTime);;
        this.statistic = statistic;
    }
}
