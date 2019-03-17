package com.example.wifiserver;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserResp extends CrudRepository<Uuser,Integer> {
    Boolean existsByName(String name);
    Boolean existsByNameAndPassword(String name,String password);
    Boolean existsByIdAndName(int id,String name);
    Uuser findByName(String name);
    void deleteByName(String name);
    @Query(value = "update uuser set password=?1 where name=?2 ", nativeQuery = true)
    @Modifying
    void updateOne(String password,String name);
    @Query(value = "update uuser set share_position=true where name=?1 ", nativeQuery = true)
    @Modifying
    void op_share(String name);
    @Query(value = "update uuser set share_position=false where name=?1 ", nativeQuery = true)
    @Modifying
    void clo_share(String name);
    @Query(value = "update uuser set x_p = ?1 , y_p = ?2 where name=?3 ", nativeQuery = true)
    @Modifying
    void update_position(int x_p,int y_p,String name);


}
