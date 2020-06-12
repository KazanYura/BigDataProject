package com.spring.services;

import com.spring.entities.GroupEntity;
import com.spring.entities.WindowOutFirstEntity;
import com.spring.repositories.WindowFirstRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WindowFirstServiceImpl implements WindowFirstServiceInterface{
    @Autowired
    private WindowFirstRepository repository;

    @Override
    public List<WindowOutFirstEntity> findAllByTimestampID(long timestamp) {
        return repository.findAllByTimestampID(timestamp);
    }
}