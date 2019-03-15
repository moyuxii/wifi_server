package com.example.wifiserver;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface wifi_infoResp extends CrudRepository<wifi_info,Integer> {
    @Query(value = "select * from wifi_info  ww where ww.bssid = ?1",nativeQuery = true)
    List<wifi_info> findAllByBssid(String bssid);
    @Query(value = "select * from wifi_info ww where ww.position_id = ?1",nativeQuery = true)
    List<wifi_info> findAllByPosition_id(String position_id);
}
