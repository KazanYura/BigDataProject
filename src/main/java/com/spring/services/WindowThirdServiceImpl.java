package com.spring.services;

import com.spring.entities.WindowOutThirdEntity;
import com.spring.repositories.WindowThirdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WindowThirdServiceImpl implements WindowThirdServiceInterface{
    @Autowired
    private WindowThirdRepository repository;

    @Override
    public List<WindowOutThirdEntity> findAllByTimestampID(long timestamp) {
        return repository.findAllByTimestampID(timestamp);
    }
}