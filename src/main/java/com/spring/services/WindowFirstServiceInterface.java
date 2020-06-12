package com.spring.services;

import com.spring.entities.WindowOutFirstEntity;

import java.util.List;

public interface WindowFirstServiceInterface {

    List<WindowOutFirstEntity> findAllByTimestampID(long timestamp);
}
