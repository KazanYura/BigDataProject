package com.spring.repositories;

import com.spring.entities.WindowOutSecondEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WindowSecondRepository extends JpaRepository<WindowOutSecondEntity, Long> {
    @Query(value = "SELECT * FROM statesstat  where timestampID > ?1 - 10800000",nativeQuery = true)
    List<WindowOutSecondEntity> findAllByTimestampID(long timestamp);
}