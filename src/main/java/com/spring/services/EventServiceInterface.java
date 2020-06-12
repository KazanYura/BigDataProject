package com.spring.services;

import com.spring.entities.EventEntity;

import java.util.List;

public interface EventServiceInterface {

    List<EventEntity> findAllByEventID(String eventID);
    List<EventEntity> findAllByGroupID(int groupID);
}
