package com.example.wifiserver;

import org.springframework.data.repository.CrudRepository;

public interface friendshipResp extends CrudRepository<friendship,Integer> {
    boolean existsByUuserAndFriend(String name,String friend);
    void deleteByUuserAndFriend(String name,String friend);
}
