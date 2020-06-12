package com.spring.responses;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

public class StateWindowResponse {
    public String startTime;
    public String endTime;
    public HashMap<String, List<String>> statistic;
    public StateWindowResponse(long startTime, long endTime, HashMap<String, List<String>> statistic) throws JsonProcessingException {
        this.startTime = new SimpleDateFormat("HH:mm").format(startTime);
        this.endTime = new SimpleDateFormat("HH:mm").format(endTime);;
        this.statistic = statistic;
    }
}
