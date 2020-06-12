package com.spring.services;

import com.spring.entities.GroupEntity;
import com.spring.repositories.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupServiceInterface{
    @Autowired
    private GroupRepository repository;
    @Override
    public List<GroupEntity> findGroupsByCity(String eventID) {
        return repository.findGroupByCityName(eventID);
    }
}
