package com.spring.repositories;

import com.spring.entities.WindowOutThirdEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WindowThirdRepository extends JpaRepository<WindowOutThirdEntity, Long> {
    @Query(value = "SELECT * FROM countryTopic  where timestampID > ?1 - 21600000",nativeQuery = true)
    List<WindowOutThirdEntity> findAllByTimestampID(long timestamp);
}