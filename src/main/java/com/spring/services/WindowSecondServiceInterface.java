package com.spring.services;

import com.spring.entities.WindowOutSecondEntity;

import java.util.List;

public interface WindowSecondServiceInterface {

    List<WindowOutSecondEntity> findAllByTimestampID(long timestamp);
}
