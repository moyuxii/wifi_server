package com.example.wifiserver;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.groups.Default;

@Entity
public class Uuser {
    @GeneratedValue(strategy= GenerationType.IDENTITY)  /*按主键自增*/
    @Id
    private Integer id;
    private String name;
    private String password;
    private int x_p;
    private int y_p;
    private boolean share_position;
    Uuser(String name,String password){
        this.name = name;
        this.password = password;
    }
    Uuser(){};
    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setY_p(int y_p) {
        this.y_p = y_p;
    }

    public void setX_p(int x_p) {
        this.x_p = x_p;
    }

    public void setShare_position(boolean share_position) {
        this.share_position = share_position;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getY_p() {
        return y_p;
    }

    public int getX_p() {
        return x_p;
    }

    public boolean getShare_position(){
        return share_position;
    }
}

