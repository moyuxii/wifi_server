package com.example.wifiserver;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class friendship {
    @Id
    private Integer id;
    private String uuser;
    private String friend;

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
