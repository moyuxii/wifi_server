package com.example.wifiserver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Uuser {
    @GeneratedValue(strategy= GenerationType.IDENTITY)  /*按主键自增*/
    @Id
    private Integer id;
    private String name;
    private String passward;
    Uuser(String name,String passward){
        this.name = name;
        this.passward = passward;
    }
    Uuser(){};
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassward(String passward) {
        this.passward = passward;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassward() {
        return passward;
    }
}

