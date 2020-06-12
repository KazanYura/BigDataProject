package com.spring.services;

import com.spring.entities.WindowOutSecondEntity;
import com.spring.repositories.WindowSecondRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WindowSecondServiceImpl implements WindowSecondServiceInterface{
    @Autowired
    private WindowSecondRepository repository;

    @Override
    public List<WindowOutSecondEntity> findAllByTimestampID(long timestamp) {
        return repository.findAllByTimestampID(timestamp);
    }
}