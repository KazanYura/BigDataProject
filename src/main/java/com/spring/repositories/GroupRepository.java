package com.spring.repositories;

import com.spring.entities.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
    @Query(value = "SELECT grouptable.ID,grouptable.groupName,cities.cityName FROM grouptable left join cities on cities.ID = grouptable.groupCityID where cityName=?1",nativeQuery = true)
    List<GroupEntity> findGroupByCityName(String name);
}