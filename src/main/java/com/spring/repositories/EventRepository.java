package com.spring.repositories;

import com.spring.entities.CityEntity;
import com.spring.entities.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
    @Query(value = "SELECT eventtable.ID,eventtable.eventName,eventtable.eventTime,grouptable.groupName,cities.cityName,countries.countryName,x.topics FROM (eventtable left join grouptable on eventtable.groupID = grouptable.ID left join cities on grouptable.groupCityID = cities.ID left join countries on cities.countryID = countries.ID left join (select grouptopic.groupID, group_concat(grouptopic.topicName) as topics from grouptopic group by grouptopic.groupID) x on grouptable.ID = x.groupID) where eventtable.ID = ?1",nativeQuery = true)
    List<EventEntity> findAllByEventID(String id);

    @Query(value = "SELECT eventtable.ID,eventtable.eventName,eventtable.eventTime,grouptable.groupName,cities.cityName,countries.countryName,x.topics FROM (eventtable left join grouptable on eventtable.groupID = grouptable.ID left join cities on grouptable.groupCityID = cities.ID left join countries on cities.countryID = countries.ID left join (select grouptopic.groupID, group_concat(grouptopic.topicName) as topics from grouptopic group by grouptopic.groupID) x on grouptable.ID = x.groupID) where grouptable.ID = ?1",nativeQuery = true)
    List<EventEntity> findAllByGroupID(int id);
}
