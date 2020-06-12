package com.spring.responses;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class TopicResponse {
    public String startTime;
    public String endTime;
    public Map<String, HashMap<String,Integer>> statistic;
    public TopicResponse(long startTime, long endTime, HashMap<String, HashMap<String,Integer>> statistic) throws JsonProcessingException {
        this.startTime = new SimpleDateFormat("HH:mm").format(startTime);
        this.endTime = new SimpleDateFormat("HH:mm").format(endTime);;
        this.statistic = statistic;
    }
}