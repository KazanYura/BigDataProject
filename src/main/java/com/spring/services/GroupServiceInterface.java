package com.spring.services;

import com.spring.entities.GroupEntity;

import java.util.List;

public interface GroupServiceInterface {

    List<GroupEntity> findGroupsByCity(String cityName);
}
