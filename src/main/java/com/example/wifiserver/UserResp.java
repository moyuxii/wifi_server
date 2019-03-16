package com.example.wifiserver;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserResp extends CrudRepository<Uuser,Integer> {
    Boolean existsByName(String name);
    @Query(value = "select * from uuser uu where uu.passward =  ?1",nativeQuery = true)
    Uuser findByPassward(String passward);
}
