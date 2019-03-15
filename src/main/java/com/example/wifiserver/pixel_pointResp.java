package com.example.wifiserver;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface pixel_pointResp extends CrudRepository<pixel_point, Integer> {
    @Query(value = "select * from pixel_point pp where pp.position = ?1",nativeQuery = true)
    pixel_point findByPosition(String position);

}
