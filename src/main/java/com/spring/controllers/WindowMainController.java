package com.spring.controllers;

import com.database.enums.StatesEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neovisionaries.i18n.CountryCode;
import com.spring.entities.WindowOutFirstEntity;
import com.spring.entities.WindowOutSecondEntity;
import com.spring.entities.WindowOutThirdEntity;
import com.spring.responses.StateWindowResponse;
import com.spring.responses.TopicResponse;
import com.spring.responses.WindowResponse;
import com.spring.services.WindowFirstServiceInterface;
import com.spring.services.WindowSecondServiceInterface;
import com.spring.services.WindowThirdServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class WindowMainController {
    @Autowired
    private WindowSecondServiceInterface windowSecondServiceInterface;
    @Autowired
    private WindowFirstServiceInterface windowFirstServiceInterface;
    @Autowired
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private WindowThirdServiceInterface windowThirdServiceInterface;

    @GetMapping("/getFirstAnalysis")
    @ResponseBody
    public WindowResponse getFirstTest() throws IOException {
        long time = System.currentTimeMillis();
        time = time - (time % 3600000) - 3600000;
        List<WindowOutFirstEntity> windowOutFirstEntities = windowFirstServiceInterface.findAllByTimestampID(time);
        HashMap<String, Integer> results = new HashMap<>();
        HashMap<String, Integer> eResults = new HashMap<>();
        for (WindowOutFirstEntity json : windowOutFirstEntities) {
            HashMap<String, Integer> res = objectMapper.readValue(json.getCountriesJSON(), HashMap.class);
            res.forEach((key, value) -> results.merge(key, value, Integer::sum));
        }
        for (String k : results.keySet()) {
            eResults.put(CountryCode.getByCode(k.toUpperCase()).getName(), results.get(k));
        }
        return new WindowResponse(time - 21600000, time, eResults);
    }

    @GetMapping("/getSecondAnalysis")
    @ResponseBody
    public StateWindowResponse getSecondTest() throws IOException {
        long time = System.currentTimeMillis();
        time = time - (time % 3600000) - 3600000;
        List<WindowOutSecondEntity> windowOutSecondEntities = windowSecondServiceInterface.findAllByTimestampID(time);
        HashMap<String, List<String>> results = new HashMap<>();
        HashMap<String, List<String>> eResults = new HashMap<>();
        for (WindowOutSecondEntity json : windowOutSecondEntities) {
            if (results.containsKey(json.getState())) {
                List<String> tmp = new ArrayList<>(results.get(json.getState()));
                tmp.add(json.getName());
                results.put(json.getState(), tmp);
            } else {
                results.put(json.getState(), Collections.singletonList(json.getName()));
            }
        }
        for (String k : results.keySet()) {
            eResults.put(StatesEnum.valueOfAbbreviation(k.toUpperCase()).getName(), results.get(k).stream().distinct().collect(Collectors.toList()));
        }
        return new StateWindowResponse(time - 10800000, time, eResults);
    }
    @GetMapping("/getThirdAnalysis")
    @ResponseBody
    public TopicResponse getThirdAnalysis() throws IOException {
        long time = System.currentTimeMillis();
        time = time - (time % 3600000) - 3600000;
        List<WindowOutThirdEntity> windowOutThirdEntities = windowThirdServiceInterface.findAllByTimestampID(time);
        Set<WindowOutThirdEntity> s = new HashSet<>(windowOutThirdEntities);
        windowOutThirdEntities = new ArrayList<>(s);
        HashMap<String, HashMap<String,Integer>> results = new HashMap<>();
        HashMap<String, HashMap<String,Integer>> eResults = new HashMap<>();
        for (WindowOutThirdEntity json : windowOutThirdEntities) {
            HashMap<String, Integer> tmp;
            if (results.containsKey(json.getCountry())) {
                tmp = results.get(json.getCountry());
                if (tmp.containsKey(json.getTopic())){
                    int f = tmp.get(json.getTopic());
                    tmp.put(json.getTopic(),f + json.getFrequency());
                }else{
                    tmp.put(json.getTopic(),json.getFrequency());
                }
            } else {
                tmp = new HashMap<>();
                tmp.put(json.getTopic(),json.getFrequency());
            }
            results.put(json.getCountry(),tmp);
        }
        for (String k : results.keySet()) {
            String max_key = Collections.max(results.get(k).entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
            HashMap<String,Integer> tmp = new HashMap<>();
            tmp.put(max_key,results.get(k).get(max_key));
            eResults.put(CountryCode.getByCode(k.toUpperCase()).getName(),tmp );
        }
        return new TopicResponse(time - 21600000, time, eResults);
    }
}
