package com.example.wifiserver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class friendship {
    @GeneratedValue(strategy= GenerationType.IDENTITY)  /*按主键自增*/
    @Id
    private Integer id;
    private String uuser;
    private String friend;

    friendship(String uuser,String friend){
        this.uuser = uuser;
        this.friend =friend;
    }
    friendship(){};

    public void setId(int id) {
        this.id = id;
    }

    public void setFriend(String friend) {
        this.friend = friend;
    }

    public void setUser(String uuser) {
        this.uuser = uuser;
    }

    public String getUuser() {
        return uuser;
    }

    public Integer getId() {
        return id;
    }

    public String getFriend() {
        return friend;
    }
}
