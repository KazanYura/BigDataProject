package com.spring.controllers;

import com.neovisionaries.i18n.CountryCode;
import com.spring.entities.EventEntity;
import com.spring.requests.ByGroupRequest;
import com.spring.requests.EventRequest;
import com.spring.responses.EventResponse;
import com.spring.services.EventServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
public class EventController {
    @Autowired
    private EventServiceInterface eventServiceInterface;
    @PostMapping("/getEventByEventID")
    public ResponseEntity getEvent(@RequestBody EventRequest eventRequest){
        List<EventEntity> eventEntityList = eventServiceInterface.findAllByEventID(eventRequest.getEventID());
        EventEntity event = eventEntityList.get(0);
        String[] topics = event.getTopics().split(",");
        CountryCode code = CountryCode.getByCode(event.getCountryName().toUpperCase());
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
        String date = sdf.format(event.getEventTime());
        EventResponse eventResponse = new EventResponse(event.getID(),event.getEventName(),date,event.getGroupName(),event.getCityName(),code.getName(),topics);
        return ResponseEntity.ok(eventResponse);
    }
    @PostMapping("/getEventByGroupID")
    public ResponseEntity getEvent(@RequestBody ByGroupRequest groupRequest){
        ArrayList<EventResponse> responseArrayList = new ArrayList<>();
        List<EventEntity> eventEntityList = eventServiceInterface.findAllByGroupID(groupRequest.getGroupID());
        for (EventEntity event: eventEntityList) {
            String[] topics = event.getTopics().split(",");
            CountryCode code = CountryCode.getByCode(event.getCountryName().toUpperCase());
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy 'at' h:mm a");
            String date = sdf.format(event.getEventTime());
            responseArrayList.add(new EventResponse(event.getID(), event.getEventName(), date, event.getGroupName(), event.getCityName(), code.getName(), topics));
        }
        return ResponseEntity.ok(responseArrayList.toArray());
    }
}
