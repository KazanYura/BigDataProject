package com.spring.services;

import com.spring.entities.WindowOutThirdEntity;

import java.util.List;

public interface WindowThirdServiceInterface {

    List<WindowOutThirdEntity> findAllByTimestampID(long timestamp);
}
