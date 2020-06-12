package com.spring.repositories;

import com.spring.entities.WindowOutFirstEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface WindowFirstRepository extends JpaRepository<WindowOutFirstEntity, Long>{
    @Query(value = "SELECT * FROM windowstorage  where timestampID > ?1 - 21600000",nativeQuery = true)
    List<WindowOutFirstEntity> findAllByTimestampID(long timestamp);
}
