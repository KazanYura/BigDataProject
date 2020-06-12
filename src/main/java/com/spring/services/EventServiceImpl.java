package com.spring.services;

import com.spring.entities.EventEntity;
import com.spring.entities.GroupEntity;
import com.spring.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class EventServiceImpl implements EventServiceInterface{
    @Autowired
    private EventRepository repository;
    @Override
    public List<EventEntity> findAllByEventID(String eventID) {
        return repository.findAllByEventID(eventID);
    }

    @Override
    public List<EventEntity> findAllByGroupID(int groupID) {
        return repository.findAllByGroupID(groupID);
    }
}
