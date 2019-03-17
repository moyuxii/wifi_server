package com.example.wifiserver;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface friendshipResp extends CrudRepository<friendship,Integer> {
    boolean existsByUuserAndFriend(String name,String friend);
    void deleteByUuserAndFriend(String name,String friend);
    @Query(value = "select * from friendship  ff where ff.uuser = ?1",nativeQuery = true)
    List<friendship> findAllByUuser(String name);
}
