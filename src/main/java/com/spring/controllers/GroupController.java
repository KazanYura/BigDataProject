package com.spring.controllers;

import com.neovisionaries.i18n.CountryCode;
import com.spring.entities.EventEntity;
import com.spring.entities.GroupEntity;
import com.spring.requests.EventRequest;
import com.spring.requests.GroupRequest;
import com.spring.responses.EventResponse;
import com.spring.services.EventServiceInterface;
import com.spring.services.GroupServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class GroupController {
    @Autowired
    private GroupServiceInterface groupServiceInterface;
    @PostMapping("/getGroup")
    public ResponseEntity getEvent(@RequestBody GroupRequest groupRequest){
        List<GroupEntity> groupEntities = groupServiceInterface.findGroupsByCity(groupRequest.getCityName());
        return ResponseEntity.ok(groupEntities.toArray());
    }
}